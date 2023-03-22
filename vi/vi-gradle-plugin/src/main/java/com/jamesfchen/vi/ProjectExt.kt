package com.jamesfchen.vi

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.BaseVariant
import com.tencent.matrix.plugin.compat.AgpCompat
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import java.io.File
import java.io.FileInputStream
import java.util.*
val nameOfSymbolDirectory = AgpCompat.getIntermediatesSymbolDirName()

fun Project.findMappingTxtFile(variant: BaseVariant): File {
    return File(project.buildDir, "outputs")
        .resolve("mapping")
        .resolve(variant.name)
        .resolve("mapping.txt")
}
fun Project.findRTxtFile(variant:BaseVariant): File {
    return  File(project.buildDir, "intermediates")
        .resolve(nameOfSymbolDirectory)
        .resolve(variant.name)
        .resolve("R.txt")
}
fun Project.findToolnm(): File {
    val adb = (extensions.findByType(BaseExtension::class.java) as BaseExtension).adbExecutable
//    val SO_ARCH = 'arm-linux-androideabi'
    val SO_ARCH = "aarch64-linux-android"
    val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
    val prebuiltPath = "${adb.parentFile.parentFile}${File.separator}ndk-bundle${File.separator}toolchains${File.separator}${SO_ARCH}-4.9${File.separator}prebuilt"
    val platform = if(isWindows) "windows-x86_64" else "linux-x86_64" //darwin-x86_64
    val nm = if(isWindows)  "${SO_ARCH}-nm.exe" else "${SO_ARCH}-nm"
    return File("${prebuiltPath}${File.separator}${platform}${File.separator}bin${File.separator}${nm}")
}
fun Project.findCWebp() = Jar.getResourceAsFile(
    "/${
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            "libwebp-1.3.0-windows-x64"
        } else if (Os.isFamily(Os.FAMILY_MAC)) {
            "libwebp-1.3.0-mac-x86-64"
        } else "libwebp-1.3.0-windows-x64"
    }/cwebp.exe", ViPlugin::class.java
)

fun Project.findApkAnalyzer() =
    Jar.getResourceAsFile("/matrix-apk-canary-2.0.8.jar", ViPlugin::class.java)

fun Project.find7Zip(): File {
    val _7zip =
        if (Os.isFamily(Os.FAMILY_WINDOWS)) "SevenZip-windows-x86_64.exe" else "SevenZip-linux-x86_64.exe"
    return Jar.getResourceAsFile("/$_7zip", ViPlugin::class.java)
}

fun Project.findZipalign(): File {
    val zipalign = if (Os.isFamily(Os.FAMILY_WINDOWS)) "zipalign.exe" else "zipalign"
    return File(findBuildTools(), zipalign)
}

fun Project.findApksigner(): File {
    val apksigner = if (Os.isFamily(Os.FAMILY_WINDOWS)) "apksigner.bat" else "apksigner"
    return File(findBuildTools(), apksigner)
}

fun Project.findBuildTools(): File {
    val extension = project.extensions.findByType(BaseExtension::class.java)
    extension?.buildToolsVersion
        ?: throw java.lang.IllegalArgumentException("不存在build tools,可能需要配置")
    return findBuildTools(extension.buildToolsVersion)
}

fun Project.findBuildTools(buildToolsVersion: String): File {
    return File("${findSdkLocation()}/build-tools/${buildToolsVersion}")
}

fun Project.findAndroidJar(sdk: Int): ConfigurableFileCollection =
    files("${findSdkLocation()}/platforms/android-$sdk/android.jar")

fun Project.findSdkLocation(): File {
    val rootDir = project.rootDir
    val localProperties = File(rootDir, "local.properties")
    if (localProperties.exists()) {
        val properties = Properties()
        FileInputStream(localProperties).use { instr ->
            properties.load(instr)
        }
        var sdkDirProp = properties.getProperty("sdk.dir")
        return if (sdkDirProp != null) {
            File(sdkDirProp)
        } else {
            sdkDirProp = properties.getProperty("android.dir")
            if (sdkDirProp != null) {
                File(rootDir, sdkDirProp)
            } else {
                throw RuntimeException("No sdk.dir property defined in local.properties file.")
            }
        }
    } else {
        val envVar = System.getenv("ANDROID_HOME")
        if (envVar != null) {
            return File(envVar)
        } else {
            val property = System.getProperty("android.home")
            if (property != null) {
                return File(property)
            }
        }
    }
    throw RuntimeException("Can't find SDK path")
}
