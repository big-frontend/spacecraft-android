package com.jamesfchen.vi.apk.minify

import com.android.build.gradle.api.BaseVariant
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jamesfchen.vi.findApkAnalyzer
import com.jamesfchen.vi.findMappingTxtFile
import com.jamesfchen.vi.findRTxtFile
import com.jamesfchen.vi.findToolnm
import com.tencent.matrix.javalib.util.Log
import com.tencent.matrix.javalib.util.Pair
import com.tencent.matrix.javalib.util.Util
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File

/**
 * 可优化资源计算器
 */
class ApkAnalyzer(
    private val project: Project,
    private val variant: BaseVariant,
    private val resourceMinifyExtension: ResourceMinifyExtension,
    private val soMinifyExtension: SoMinifyExtension,
) {
    private val pathOfApkChecker: String by lazy {
        val apkAnalyzer = project.findApkAnalyzer()
        // Validate path of ApkChecker tool
        if (!apkAnalyzer.exists()) {
            throw GradleException("the path of Matrix-ApkChecker ${apkAnalyzer.absolutePath} is not exist!")
        }
        return@lazy apkAnalyzer.absolutePath
    }
    private val rTxtFile = project.findRTxtFile(variant)
    private val mappingTxtFile = project.findMappingTxtFile(variant)

    // result
    // Map of resource names parsed from R.txt
    val mapOfResources: MutableMap<String, Int> = HashMap()

    // Map of styleables parsed from R.txt
    val mapOfStyleables: MutableMap<String, Array<Pair<String, Int>>> = HashMap()

    // Compute resource to be removed, replaced and obfuscated
    val mapOfResourcesGonnaRemoved: MutableMap<String, Int> = HashMap()
    val mapOfDuplicatesReplacements: MutableMap<String, String> = HashMap()
    val mapOfObfuscatedNames = HashMap<String, String>()
    val mapOfAssetsGonnaRemoved: MutableMap<String, Int> = HashMap()

    // Find out unused resources assets so
    private val resultOfUnusedResources: MutableSet<String> = HashSet()
    private val resultOfDuplicatesResources: MutableMap<String, MutableSet<String>> = HashMap()
    private val resultOfUnusedAssets: MutableSet<String> = HashSet()
    private val resultOfUnstrippedSo: MutableSet<String> = HashSet()

    fun calculate(originalApkFile: File,) {
        val startTime = System.currentTimeMillis()
        findOutAll(originalApkFile)
        Log.i(MinifyTask.TAG, "find out %d unused resources:\n %s", resultOfUnusedResources.size, resultOfUnusedResources)
        Log.i(MinifyTask.TAG, "find out %d duplicated resources:", resultOfDuplicatesResources.size)
        for (md5 in resultOfDuplicatesResources.keys) {
            Log.i(MinifyTask.TAG, "> md5:%s, files:%s", md5, resultOfDuplicatesResources[md5])
        }
        Log.i(MinifyTask.TAG, "find out %d unused assets:\n %s", resultOfUnusedAssets.size, resultOfUnusedAssets)
        Log.i(MinifyTask.TAG, "find out %d unstripped so:\n %s", resultOfUnstrippedSo.size, resultOfUnstrippedSo)
        Log.i(MinifyTask.TAG, "find out all cost time %fs ", (System.currentTimeMillis() - startTime) / 1000.0f)

        // Parse R.txt
        ApkUtil.readResourceTxtFile(rTxtFile, mapOfResources, mapOfStyleables)
        calculateResources()
        calculateAssets()
    }

    private fun findOutAll(originalApkFile: File) {
        val pathOfBuildDir = project.buildDir.canonicalPath
        val pathOfOutputWithoutSuffix = File(pathOfBuildDir, "apk_checker").absolutePath

        val parameters = ArrayList<String>()
        parameters.add("java")
        parameters.add("-jar")
        parameters.add(pathOfApkChecker)
        parameters.add("--apk")
        parameters.add(originalApkFile.absolutePath)
        parameters.add("--output")
        parameters.add(pathOfOutputWithoutSuffix)

        if (mappingTxtFile.exists()) {
            parameters.add("--mappingTxt")
            parameters.add(mappingTxtFile.absolutePath)
        }

        parameters.add("--format")
        parameters.add("json")
        if (resourceMinifyExtension.shrinkArsc) {
            parameters.add("-unusedResources")
            parameters.add("--rTxt")
            parameters.add(rTxtFile.absolutePath)
            val parametersOfIgnoredResources = StringBuilder()
            if (resourceMinifyExtension.ignoreResources.isNotEmpty()) {
                for (ignore in resourceMinifyExtension.ignoreResources) {
                    parametersOfIgnoredResources.append(ignore)
                    parametersOfIgnoredResources.append(',')
                }
                parametersOfIgnoredResources.deleteCharAt(parametersOfIgnoredResources.length - 1)
                parameters.add("--ignoreResources")
                parameters.add(parametersOfIgnoredResources.toString())
            }
        }
        if (resourceMinifyExtension.shrinkAssets) {
            parameters.add("-unusedAssets")
            val parametersOfIgnoredResources = StringBuilder()
            if (resourceMinifyExtension.ignoreAssets.isNotEmpty()) {
                for (ignore in resourceMinifyExtension.ignoreAssets) {
                    parametersOfIgnoredResources.append(ignore)
                    parametersOfIgnoredResources.append(',')
                }
                parametersOfIgnoredResources.deleteCharAt(parametersOfIgnoredResources.length - 1)
                parameters.add("--ignoreAssets")
                parameters.add(parametersOfIgnoredResources.toString())
            }
        }
        if (soMinifyExtension.shrinkSo) {
            parameters.add("-unstrippedSo")
            parameters.add("--toolnm")
            parameters.add(project.findToolnm().absolutePath)
        }
        if (resourceMinifyExtension.shrinkDuplicates) {
            parameters.add("-duplicatedFile")
        }
        val process = ProcessBuilder().command(parameters).start()
        ApkUtil.waitForProcessOutput(process)
        if (process.exitValue() != 0) {
            throw GradleException(
                process.errorStream.bufferedReader().readLines().joinTo(StringBuilder(), "\n")
                    .toString()
            )
        }
        val checkerOutputFile = File(pathOfBuildDir, "apk_checker.json")   // -_-|||
        if (checkerOutputFile.exists()) {
            val jsonArray = Gson().fromJson(checkerOutputFile.readText(), JsonArray::class.java)
            for (i in 0 until jsonArray.size()) {
                val jsonObject = jsonArray.get(i).asJsonObject
                parseUnusedResources(jsonObject)
                parseDuplicateResources(jsonObject)
                parseUnusedAssets(jsonObject)
                parseUnStrippedSo(jsonObject)
            }
        }
    }

    private fun parseDuplicateResources(jsonObject: JsonObject) {
        if (jsonObject.asJsonObject.get("taskType").asInt == 10) {
            val duplicatedFiles = jsonObject.asJsonObject.get("files").asJsonArray
            for (k in 0 until duplicatedFiles.size()) {
                val obj = duplicatedFiles.get(k).asJsonObject
                val md5 = obj.get("md5")
                resultOfDuplicatesResources[md5.toString()] = HashSet()
                val fileList = obj.get("files").asJsonArray
                for (m in 0 until fileList.size()) {
                    resultOfDuplicatesResources[md5.toString()]?.add(
                        fileList.get(m).asString.replace(
                            "\\", "/"
                        )
                    )
                }
            }
        }
    }
    private fun parseUnStrippedSo(jsonObject: JsonObject) {
        if (jsonObject.asJsonObject.get("taskType").asInt == 14) {
            val resList = jsonObject.asJsonObject.get("unstripped-lib").asJsonArray
            for (j in 0 until resList.size()) {
                resultOfUnstrippedSo.add(resList.get(j).asString.replace("\\", "/"))
            }
        }
    }

    private fun parseUnusedAssets(jsonObject: JsonObject) {
        if (jsonObject.asJsonObject.get("taskType").asInt == 13) {
            val resList = jsonObject.asJsonObject.get("unused-assets").asJsonArray
            for (j in 0 until resList.size()) {
                resultOfUnusedAssets.add(resList.get(j).asString.replace("\\", "/"))
            }
        }
    }

    private fun parseUnusedResources(jsonObject: JsonObject) {
        if (jsonObject.asJsonObject.get("taskType").asInt == 12) {
            val resList = jsonObject.asJsonObject.get("unused-resources").asJsonArray
            for (j in 0 until resList.size()) {
                resultOfUnusedResources.add(resList.get(j).asString.replace("\\", "/"))
            }
        }
    }

    private fun checkIfIgnored(nameOfRes: String, ignored: Set<String>): Boolean {
        for (path in ignored) {
            if (nameOfRes.matches(path.toRegex())) {
                return true
            }
        }
        return false
    }

    private fun calculateAssets() {
        // Prepare unused resources
        val regexpRules = HashSet<String>()
        for (res in resourceMinifyExtension.ignoreAssets) {
            regexpRules.add(Util.globToRegexp(res))
        }

        for (resName in resultOfUnusedAssets) {
            if (!checkIfIgnored(resName, regexpRules)) {
                mapOfAssetsGonnaRemoved[resName] = 0
            } else {
                Log.i(MinifyTask.TAG, "ignore remove unused assets %s", resName)
            }
        }

        Log.i(MinifyTask.TAG, "unused assets count:%d", resultOfUnusedAssets.size)
    }
    private fun calculateResources() {

        // Prepare unused resources
        val regexpRules = HashSet<String>()
        for (res in resourceMinifyExtension.ignoreResources) {
            regexpRules.add(Util.globToRegexp(res))
        }

        for (resName in resultOfUnusedResources) {
            if (!checkIfIgnored(resName, regexpRules)) {
                val resId = mapOfResources.remove(resName)
                if (resId != null) {
                    mapOfResourcesGonnaRemoved[resName] = resId
                }
            } else {
                Log.i(MinifyTask.TAG, "ignore remove unused resources %s", resName)
            }
        }

        Log.i(MinifyTask.TAG, "unused resources count:%d", resultOfUnusedResources.size)

        // Prepare duplicated resources
        for (md5 in resultOfDuplicatesResources.keys) {
            val duplicatesEntries = resultOfDuplicatesResources[md5]
            val duplicatesNames = HashMap<String, String>()
            if (duplicatesEntries != null) {
                for (entry in duplicatesEntries) {
                    if (!entry.startsWith("res/")) {
                        Log.w(MinifyTask.TAG, "   %s is not resource file!", entry)
                        continue
                    } else {
                        duplicatesNames[entry] = ApkUtil.entryToResourceName(entry)
                    }
                }
            }

            if (duplicatesNames.size > 0) {
                if (!ApkUtil.isSameResourceType(duplicatesNames.keys)) {
                    Log.w(
                        MinifyTask.TAG, "the type of duplicated resources %s are not same!", duplicatesEntries)
                    continue
                } else {
                    var it = duplicatesNames.keys.iterator()
                    while (it.hasNext()) {
                        val entry = it.next()
                        if (!mapOfResources.containsKey(duplicatesNames[entry])) {
                            Log.w(MinifyTask.TAG, "can not find duplicated resources %s!", entry)
                            it.remove()
                        }
                    }

                    if (duplicatesNames.size > 1) {
                        it = duplicatesNames.keys.iterator()
                        val replace = it.next()
                        while (it.hasNext()) {
                            val dup = it.next()
                            Log.i(MinifyTask.TAG, "replace %s with %s", dup, replace)
                            mapOfDuplicatesReplacements[dup] = replace
                        }
                    }
                }
            }
        }
        if (!resourceMinifyExtension.obfuscateArsc) return
        // Prepare proguard resource name
        val mapOfResTypeName = HashMap<String, HashSet<String>>()
        for (name in mapOfResources.keys) {
            val type = ApkUtil.parseResourceType(name)
            if (!mapOfResTypeName.containsKey(type)) {
                mapOfResTypeName[type] = HashSet()
            }
            mapOfResTypeName[type]!!.add(name)
        }

        for (resType in mapOfResTypeName.keys) {

            val resTypeBuilder = ProguardStringBuilder()
            val resNames = mapOfResTypeName[resType]

            if (resNames != null) {
                for (resName in resNames) {
                    if (!checkIfIgnored(resName, regexpRules)) {
                        mapOfObfuscatedNames[resName] =
                            "R." + resType + "." + resTypeBuilder.generateNextProguard()
                    } else {
                        Log.i(MinifyTask.TAG, "ignore proguard resource name %s", resName)
                    }
                }
            }
        }

    }
}