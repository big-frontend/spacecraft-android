@file:JvmName("StoreUtil")
package com.jamesfchen.common.util

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

/**
 * /cache
 */
val getDownloadCacheDir = Environment.getDownloadCacheDirectory()

/**
 * /system
 */
val getSystemDir = Environment.getRootDirectory()

/**
 * /data
 */
@get:JvmName("getDataDir")
val getDataDir = Environment.getDataDirectory()
/**
 * /data/data/package
 */
fun Context.getInternalAppDataDir(): File {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        return File(applicationInfo.dataDir)
    }
    return dataDir
}

/**
 * /data/data/package/xxx
 */
fun Context.getInternalAppCodeCacheDir(): File {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        return File(applicationInfo.dataDir + "/code_cache")
    }
    return codeCacheDir
}

fun Context.getInternalAppCacheDir(): File {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        return File(applicationInfo.dataDir + "/cache")
    }
    return cacheDir
}

fun Context.getInternalAppDbDir(): File {
    return File(applicationInfo.dataDir + "/databases")
}
///data/data/package/databases/name
fun Context.getInternalAppDbDir(dbFileName:String) = getDatabasePath(dbFileName)
fun Context.getInternalAppFilesDir() = filesDir
fun Context.getInternalAppSpDir() = File(applicationInfo.dataDir + "/shared_prefs")
fun Context.getInternalAppNoBackupDir() : File? {
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//    File(applicationInfo.dataDir + "/no_backup")
//    }
    return noBackupFilesDir
}

/**
 * /storage/emulated/0
 */
@get:JvmName("getExternalStorageDir")
val externalStorageDir=Environment.getExternalStorageDirectory()
/**
 * /storage/emulated/0/xxx
 */
@get:JvmName("getExternalMoviesDir")
val externalMoviesDir
    get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
@get:JvmName("getExternalDownloadsDir")
val externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
@get:JvmName("getExternalPicturesDir")
val externalPicturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
@get:JvmName("getExternalMusicDir")
val externalMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
@get:JvmName("getExternalPodcastsDir")
val externalPodcastsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS)
@get:JvmName("getExternalRingtonesDir")
val externalRingtonesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)
@get:JvmName("getExternalAlarmsDir")
val externalAlarmsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
@get:JvmName("getExternalNotificationsDir")
val externalNotificationsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS)
@get:JvmName("getExternalDcimDir")
val externalDcimDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
@get:JvmName("getExternalDocumentsDir")
val externalDocumentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//@get:JvmName("getScreenshotsDir")
//val screenshotsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_SCREENSHOTS)
//val audiobooksDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_AUDIOBOOKS)
/**
 * /storage/emulated/0/Android/data/package
 */
//@JvmName("externalAppDataDir")
fun Context.getExternalAppDataDir() = externalCacheDir?.parentFile
/**
 * /storage/emulated/0/Android/data/package/files/xxx
 */
fun Context.getExternalAppMoviesDir() = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
fun Context.getExternalAppDownloadDir() = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

fun Context.getExternalAppPicturesDir() = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

fun Context.getExternalAppMusicDir() = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
fun Context.getExternalAppPodcastsDir() = getExternalFilesDir(Environment.DIRECTORY_PODCASTS)

fun Context.getExternalAppRingtonesDir() = getExternalFilesDir(Environment.DIRECTORY_RINGTONES)

fun Context.getExternalAppAlarmsDir() = getExternalFilesDir(Environment.DIRECTORY_ALARMS)
fun Context.getExternalAppNotificationsDir() = getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)

fun Context.getExternalAppDcimDir() = getExternalFilesDir(Environment.DIRECTORY_DCIM)
fun Context.getExternalAppDocumentDir() = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

/**
 * /storage/emulated/0/Android/data/package/cache
 */
fun Context.getExternalAppCacheDir()=externalCacheDir

fun Context.getExternalAppCodeCacheDir()=externalMediaDirs
/**
 * /storage/emulated/0/Android/obb/package
 */
fun Context.getExternalAppObbDir()=obbDir





