package com.jamesfchen.vi.apk.minify

open class ApkAnalyzerExtension(var variant: Set<String> = HashSet(), var configPath:String ="")
open class MinifyExtension(
    var variant: Set<String> = HashSet(),
    var use7zip: Boolean = false,
    var useZipAlign: Boolean = false,
    var useApkSign: Boolean = false,
)
open class CodeMinifyExtension()
open class SoMinifyExtension(
    var shrinkSo: Boolean = false,
)
open class ImageExtension(
    var png2webp: Boolean = false,
)
open class ResourceMinifyExtension(
    var shrinkDuplicates: Boolean = false,
    var shrinkArsc: Boolean = false,
    var ignoreResources: Set<String> = HashSet(),
    var shrinkAssets: Boolean = false,
    var ignoreAssets: Set<String> = HashSet(),
    var obfuscateArsc: Boolean = false,
)