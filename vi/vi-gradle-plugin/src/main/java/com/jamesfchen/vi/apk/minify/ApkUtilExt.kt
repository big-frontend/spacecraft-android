package com.jamesfchen.vi.apk.minify

import com.android.utils.FileUtils
import com.jamesfchen.vi.find7Zip
import com.tencent.matrix.javalib.util.Log
import com.tencent.matrix.javalib.util.Pair
import com.tencent.matrix.javalib.util.Util
import com.tencent.mm.arscutil.ArscUtil
import com.tencent.mm.arscutil.io.ArscReader
import com.tencent.mm.arscutil.io.ArscWriter
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

const val ARSC_FILE_NAME = "resources.arsc"
const val RES_DIR_PROGUARD_NAME = "r"

private fun zipFile(zipOutputStream: ZipOutputStream, file: File, rootDir: String, compressedEntry: Set<String>) {
    if (file.isDirectory) {
        val unZipFiles = file.listFiles()
        for (subFile in unZipFiles) {
            zipFile(zipOutputStream, subFile, rootDir, compressedEntry)
        }
    } else {
        val zipEntry = ZipEntry(file.canonicalPath.substring(rootDir.length + 1))
        val method = if (compressedEntry.contains(file.canonicalPath)) ZipEntry.DEFLATED else ZipEntry.STORED
        Log.i(MinifyTask.TAG, "zip file %s -> entry %s, DEFLATED %s", file.canonicalPath, zipEntry.name, method == ZipEntry.DEFLATED)
        zipEntry.method = method
        ApkUtil.addZipEntry(zipOutputStream, zipEntry, file)
    }
}

fun File.minify(
    project:Project,
    minifyExtension: MinifyExtension,
    mapOfResources: MutableMap<String, Int>,
    mapOfStyleables: MutableMap<String, Array<Pair<String, Int>>>,
    mapOfResourcesGonnaRemoved: MutableMap<String, Int>,
    mapOfDuplicatesReplacements: MutableMap<String, String>,
    mapOfObfuscatedNames: HashMap<String, String>,

    resultOfObfuscatedDirs: HashMap<String, String>,
    resultOfObfuscatedFiles: HashMap<String, String>,
): File? {
    try {
        var zipOutputStream: ZipOutputStream? = null
        try {
            val rmUnused = mapOfResourcesGonnaRemoved.isNotEmpty()
            val rmDuplicated = mapOfDuplicatesReplacements.isNotEmpty()
            val canObfuscateArsc = mapOfObfuscatedNames.isNotEmpty()

            Log.i(MinifyTask.TAG, "rmUnsed %s, rmDuplicated %s, obfuscateArsc %s", rmUnused, rmDuplicated, canObfuscateArsc)

//            if (!(rmUnused || rmDuplicated || canObfuscateArsc)) {
//                return null
//            }
            val zipInputFile = ZipFile(this)
            val arsc = zipInputFile.getEntry(ARSC_FILE_NAME)

            val unzipDir = File(parentFile.canonicalPath + File.separator + name.substring(0, name.lastIndexOf(".")) + "_unzip")
            FileUtils.deleteRecursivelyIfExists(unzipDir)
            unzipDir.mkdir()

            val srcArscFile = File(unzipDir, ARSC_FILE_NAME)
            val destArscFile = File(unzipDir, "shrinked_${ARSC_FILE_NAME}")

            ApkUtil.unzipEntry(zipInputFile, arsc, srcArscFile.canonicalPath)

            val reader = ArscReader(srcArscFile.canonicalPath)
            val resTable = reader.readResourceTable()

            //remove unused resources
            if (rmUnused) {
                for (resName in mapOfResourcesGonnaRemoved.keys) {
                    val resourceId = mapOfResourcesGonnaRemoved[resName]
                    if (resourceId != null) {
                        ArscUtil.removeResource(resTable, resourceId, resName)
                    }
                }
            }

            //remove duplicated resources
            if (rmDuplicated) {
                val replaceIterator = mapOfDuplicatesReplacements.keys.iterator()
                while (replaceIterator.hasNext()) {
                    val sourceFile = replaceIterator.next()
                    val sourceRes = ApkUtil.entryToResourceName(sourceFile)
                    val sourceId = mapOfResources[sourceRes]!!
                    val targetFile = mapOfDuplicatesReplacements[sourceFile]
                    val targetRes = ApkUtil.entryToResourceName(targetFile)
                    val targetId = mapOfResources[targetRes]!!
                    val success = ArscUtil.replaceFileResource(resTable, sourceId, sourceFile, targetId, targetFile)
                    if (!success) {
                        Log.w(MinifyTask.TAG, "replace %s(%s) with %s(%s) failed!", sourceRes, sourceFile, targetRes, targetFile)
                        replaceIterator.remove()
                    }
                }
            }

            //proguard resource name
            if (canObfuscateArsc) {
                val resIterator = mapOfResources.keys.iterator()
                val resIdProguard = HashMap<Int, String>()
                while (resIterator.hasNext()) {
                    val resource = resIterator.next()
                    if (mapOfObfuscatedNames.containsKey(resource)) {
                        mapOfResources[resource]?.let { resIdProguard.put(it,
                            ApkUtil.parseResourceName(mapOfObfuscatedNames[resource])
                        ) }
                    }
                }
                if (resIdProguard.isNotEmpty()) {
                    ArscUtil.replaceResEntryName(resTable, resIdProguard)
                }
            }

            val dirProguard = ProguardStringBuilder()
            val dirFileProguard = HashMap<String, ProguardStringBuilder>()
            val compressedEntry = HashSet<String>()

            for (zipEntry in zipInputFile.entries()) {

                var destFile = unzipDir.canonicalPath + File.separator + zipEntry.name.replace('/', File.separatorChar)

                if (zipEntry.name.startsWith("res/")) {
                    val resourceName = ApkUtil.entryToResourceName(zipEntry.name)
                    if (!Util.isNullOrNil(resourceName)) {
                        if (mapOfResourcesGonnaRemoved.containsKey(resourceName)) {
//                            Log.i(MinifyTask.TAG, "remove unused resource %s file %s", resourceName, zipEntry.name)
                            continue
                        } else if (mapOfDuplicatesReplacements.containsKey(zipEntry.name)) {
//                            Log.i(MinifyTask.TAG, "remove duplicated resource file %s", zipEntry.name)
                            continue
                        } else {
                            if (arsc != null && canObfuscateArsc) {
                                if (mapOfResources.containsKey(resourceName)) {
                                    val dir = zipEntry.name.substring(0, zipEntry.name.lastIndexOf("/"))
                                    val suffix = zipEntry.name.substring(zipEntry.name.indexOf("."))
                                    Log.d(MinifyTask.TAG, "resource %s dir %s", resourceName, dir)
                                    if (!resultOfObfuscatedDirs.containsKey(dir)) {
                                        val proguardDir = dirProguard.generateNextProguardFileName()
                                        resultOfObfuscatedDirs[dir] = "${RES_DIR_PROGUARD_NAME}/$proguardDir"
                                        dirFileProguard[dir] = ProguardStringBuilder()
                                        Log.i(MinifyTask.TAG, "dir %s, proguard builder", dir)
                                    }
                                    resultOfObfuscatedFiles[zipEntry.name] = resultOfObfuscatedDirs[dir] + "/" + dirFileProguard[dir]!!.generateNextProguardFileName() + suffix
                                    val success = ArscUtil.replaceResFileName(resTable, mapOfResources[resourceName]!!, zipEntry.name, resultOfObfuscatedFiles[zipEntry.name])
                                    if (success) {
                                        destFile = unzipDir.canonicalPath + File.separator + resultOfObfuscatedFiles[zipEntry.name]!!.replace('/', File.separatorChar)
                                    }
                                }
                            }
                            if (zipEntry.method == ZipEntry.DEFLATED) {
                                compressedEntry.add(destFile)
                            }
                            Log.d(MinifyTask.TAG, "unzip %s to file %s", zipEntry.name, destFile)
                            ApkUtil.unzipEntry(zipInputFile, zipEntry, destFile)
                        }
                    } else {
                        Log.w(MinifyTask.TAG, "parse entry %s resource name failed!", zipEntry.name)
                    }
                } else {
                    if (!zipEntry.name.startsWith("META-INF/") || (!zipEntry.name.endsWith(".SF") && !zipEntry.name.endsWith(".MF") && !zipEntry.name.endsWith(".RSA"))) {
                        if (zipEntry.method == ZipEntry.DEFLATED) {
                            compressedEntry.add(destFile)
                        }
                        if (zipEntry.name != ARSC_FILE_NAME) {                            // has already unzip resources.arsc before
                            Log.d(MinifyTask.TAG, "unzip %s to file %s", zipEntry.name, destFile)
                            ApkUtil.unzipEntry(zipInputFile, zipEntry, destFile)
                        }
                    }
                }
            }

            val writer = ArscWriter(destArscFile.canonicalPath)
            writer.writeResTable(resTable)
            Log.i(MinifyTask.TAG, "shrink resources.arsc size %f KB", (srcArscFile.length() - destArscFile.length()) / 1024.0)
            srcArscFile.delete()
            FileUtils.copyFile(destArscFile, srcArscFile)
            destArscFile.delete()
            // Remove output apk file, no incremental
            val shrunkApkFile = File(parentFile.canonicalPath + File.separator + name.substring(0, name.indexOf(".")) + "_shrinked.apk")
            if (shrunkApkFile.exists()) {
                Log.w(MinifyTask.TAG, "output apk file %s is already exists! It will be deleted anyway!", shrunkApkFile.absoluteFile)
                shrunkApkFile.delete()
            }
            if (minifyExtension.use7zip) {
                val _7zip = project.find7Zip()
                if (!_7zip.exists()) {
                    throw GradleException("use 7zip but the path ${_7zip.absolutePath} is not exist!")
                }
                 val pathOfSevenZip =_7zip.absolutePath
                ApkUtil.sevenZipFile(
                    pathOfSevenZip,
                    unzipDir.canonicalPath + "${File.separator}*",
                    shrunkApkFile.canonicalPath,
                    false
                )
                if (compressedEntry.isNotEmpty()) {
                    Log.i(MinifyTask.TAG, "7zip %d DEFLATED files to apk", compressedEntry.size)
                    val deflateDir = File(parentFile, name.substring(0, name.lastIndexOf(".")) + "_deflated")
                    FileUtils.deleteRecursivelyIfExists(deflateDir)
                    deflateDir.mkdir()
                    for (compress in compressedEntry) {
                        val entry = compress.substring(unzipDir.canonicalPath.length + 1)
                        val deflateFile = File(deflateDir, entry)
                        deflateFile.parentFile.mkdirs()
                        deflateFile.createNewFile()
                        FileUtils.copyFile(File(compress), deflateFile)
                    }
                    ApkUtil.sevenZipFile(
                        pathOfSevenZip,
                        deflateDir.canonicalPath + "${File.separator}*",
                        shrunkApkFile.canonicalPath,
                        true
                    )
                    FileUtils.deleteRecursivelyIfExists(deflateDir)
                }
            } else {
                zipOutputStream = ZipOutputStream(FileOutputStream(shrunkApkFile))
                zipFile(zipOutputStream, unzipDir, unzipDir.canonicalPath, compressedEntry)
            }

            FileUtils.deleteRecursivelyIfExists(unzipDir)

            Log.i(MinifyTask.TAG, "shrink apk size %f KB", (length() - shrunkApkFile.length()) / 1024.0)
            return shrunkApkFile

        } finally {
            zipOutputStream?.close()
        }
    } catch (e: Exception) {
        Log.printErrStackTrace(MinifyTask.TAG, e, "remove unused resources occur error!")
        return null
    }
}