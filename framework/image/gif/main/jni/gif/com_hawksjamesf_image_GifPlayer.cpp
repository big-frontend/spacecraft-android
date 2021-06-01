//
// Created by jamesfchen on 12/31/19.
//
#include <stdio.h>
#include <stdlib.h>
#include <android/trace.h>
#include <zlib.h>
#include <unistd.h>
#include <algorithm>
#include <map>
#include <unordered_map>
#include <iostream>
#include <set>
#include <unordered_set>
#include <iterator>
#include <exception>
#include <stdexcept>


#include "gif_lib.h"
#include "GifPlayer.h"
#include "LogUtil.h"
#include "AssetUtil.h"

#define  MODULE_NAME "native/gif_jni"
using namespace ::std;

static jlong nativePlayerAddress;
/**
 * 来自asset
 */
extern "C" JNIEXPORT jlong JNICALL
setDataSource(JNIEnv *env, jobject jgifplayer, jstring jassetName, jobject jassetManager) {
    char *assetName = const_cast<char *>(env->GetStringUTFChars(jassetName, 0));
    AAssetManager *assetManager = AAssetManager_fromJava(env, jassetManager);
    jlong nativePlayerAddress = (jlong) GifPlayer::create(env, assetName, assetManager);
    LOGI(MODULE_NAME, "setDataSource-->assetName:%s , nativePlayerAddress: %d", assetName,
         nativePlayerAddress);
    return nativePlayerAddress;
}
/**
 *  来自网络的地址，来自sdcard的地址
 */
extern "C" JNIEXPORT jlong JNICALL
Java_com_hawksjamesf_image_GifPlayer_setDataSource(JNIEnv *env, jobject jgifplayer,
                                                   jstring juriPath) {
    char *uriPath = const_cast<char *>(env->GetStringUTFChars(juriPath, 0));
    jlong nativePlayerAddress = (jlong) GifPlayer::create(env, uriPath);
    LOGI(MODULE_NAME, "setDataSource-->uriPath: %s , nativePlayerAddress: %d", uriPath,
         nativePlayerAddress);
    return nativePlayerAddress;
}
extern "C" JNIEXPORT void JNICALL
bindBitmap(JNIEnv *env, jobject jgifplayer, jlong nativePlayerAddress, jobject jbitmap) {
    GifPlayer *gifPlayer = (GifPlayer *) nativePlayerAddress;
    gifPlayer->refreshReflectUtil(ReflectUtil::reflect(jgifplayer));
    gifPlayer->bindBitmap(jbitmap);
    LOGI(MODULE_NAME, "bindBitmap-->nativePlayerAddress: %d", nativePlayerAddress);
}


extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifWidth(JNIEnv *env, jobject jgifplayer,
                                                 jlong nativePlayerAddress) {
    GifPlayer *gifPlayer = (GifPlayer *) nativePlayerAddress;
    if (gifPlayer == nullptr) {
        LOGE(MODULE_NAME, "jgifplayer must not be null");
        return 0;
    }
    return gifPlayer->getGifWidth();
}
extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifHeight(JNIEnv *env, jobject jgifplayer,
                                                  jlong nativePlayerAddress) {
    GifPlayer *gifPlayer = (GifPlayer *) nativePlayerAddress;
    if (gifPlayer == nullptr) {
        LOGE(MODULE_NAME, "jgifplayer must not be null");
        return 0;
    }
    return gifPlayer->getGifHeight();
}

extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_start(JNIEnv *env, jobject jgifplayer,
                                           jlong nativePlayerAddress) {
    LOGI(MODULE_NAME, "start-->jgifplayer:%d , nativePlayerAddress: %d", jgifplayer,
         nativePlayerAddress);
//    GifPlayer *gifPlayer = (GifPlayer *)nativePlayerAddress;
    GifPlayer *gifPlayer = reinterpret_cast<GifPlayer *>(nativePlayerAddress);
    if (gifPlayer == nullptr) {
        LOGE(MODULE_NAME, "jgifplayer must not be null");
        throw runtime_error("jgifplayer must not be null");
    }
    gifPlayer->refreshReflectUtil(ReflectUtil::reflect(jgifplayer));
    gifPlayer->start();
}

/**
 * =======================================================================
 *                  map native method to java method
 * =======================================================================
 */
JNINativeMethod gifplayer_method_map[] = {
        {"setDataSource", "(Ljava/lang/String;Landroid/content/res/AssetManager;)J", (void *) setDataSource},
//        {"setDataSource", "(Ljava/lang/String;Landroid/graphics/Bitmap;)V", (void *) setDataSource},
        {"bindBitmap",    "(JLandroid/graphics/Bitmap;)V",                           (void *) bindBitmap},
};

int register_com_hawksjamesf_image_DisplayEventReceiver(JNIEnv *env) {
    jclass cl = env->FindClass("com/jamesfchen/image/GifPlayer");
    if ((env->RegisterNatives(cl, gifplayer_method_map,
                              sizeof(gifplayer_method_map) / sizeof(gifplayer_method_map[0]))) < 0) {
        return -1;
    }
    return 0;
}

