//
// Created by hawks.jamesf on 12/31/19.
//
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/trace.h>
#include <zlib.h>
#include <unistd.h>
#include "logutil.h"
#include "gif_lib.h"
#include <algorithm>
#include "assetutil.h"
#include "GifPlayer.h"
#include <android/bitmap.h>
#include <hash_map>
#include <map>
#include <unordered_map>
#include <iostream>
#include <set>
#include <hash_set>
#include <unordered_set>
#include <iterator>
#include "GifPlayer.h"

#define  MODULE_NAME "module_gif"

using namespace ::std;
static map<jobject, GifPlayer *> gifmap;
static set<jobject> gifset;

JNIEXPORT jint JNICALL JNI_Onload(JavaVM *vm, void *reserved) {
//    ATrace_beginSection("JNI_Onload");
//    ATrace_endSection();
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {}

/**
 * 来自asset
 */
extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_setDataSource__Ljava_lang_String_2Landroid_content_res_AssetManager_2Landroid_graphics_Bitmap_2(
        JNIEnv *env, jobject jgifplayer,
        jstring jassetName, jobject jassetManager,
        jobject jbitmap) {
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) {
        AndroidBitmapInfo bitmapInfo;
        AndroidBitmap_getInfo(env, jbitmap, &bitmapInfo);
        char *assetName = const_cast<char *>(env->GetStringUTFChars(jassetName, 0));
        AAssetManager *assetManager = AAssetManager_fromJava(env, jassetManager);
        gifPlayer = AssetsGifPlayer::createAndBind(&bitmapInfo, assetName, assetManager);
        gifmap[jgifplayer] = gifPlayer;
    }
}
/**
 *  来自网络的地址，来自sdcard的地址
 */
extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_setDataSource__Ljava_lang_String_2Landroid_graphics_Bitmap_2(
        JNIEnv *env, jobject jgifplayer,
        jstring juriPath, jobject jbitmap) {
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) {
        AndroidBitmapInfo bitmapInfo;
        AndroidBitmap_getInfo(env, jbitmap, &bitmapInfo);
        char *uriPath = const_cast<char *>(env->GetStringUTFChars(juriPath, 0));
        gifPlayer = UriGifPlayer::createAndBind(&bitmapInfo, uriPath);
        gifmap[jgifplayer] = gifPlayer;
    }
}

// function contents
static int jniGetFDFromFileDescriptor(JNIEnv *env, jobject fileDescriptor) {
    jint fd = -1;
    jclass fdClass = env->FindClass("java/io/FileDescriptor");

    if (fdClass != NULL) {
        jfieldID fdClassDescriptorFieldID = env->GetFieldID(fdClass, "fd", "I");
        if (fdClassDescriptorFieldID != NULL && fileDescriptor != NULL) {
            fd = env->GetIntField(fileDescriptor, fdClassDescriptorFieldID);
        }
    }

    return fd;
}

extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_setDataSource__Ljava_io_FileDescriptor_2JJ(
        JNIEnv *env, jobject jgifplayer,
        jobject fileDescriptor, jlong offset,
        jlong length) {
    int fd = jniGetFDFromFileDescriptor(env, fileDescriptor);
}

extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifWidth(JNIEnv *env, jobject jgifplayer) {
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) throw "jgifplayer must not be null";
    return gifPlayer->getGifWidth();
}
extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifHeight(JNIEnv *env, jobject jgifplayer) {
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) throw "jgifplayer must not be null";
    return gifPlayer->getGifHeight();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_start(JNIEnv *env, jobject jgifplayer) {
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) throw "jgifplayer must not be null";
    gifPlayer->start();
}

