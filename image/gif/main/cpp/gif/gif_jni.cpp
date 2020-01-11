//
// Created by hawks.jamesf on 12/31/19.
//
#include <stdio.h>
#include <stdlib.h>
#include <android/trace.h>
#include <zlib.h>
#include <unistd.h>
#include <algorithm>
#include <hash_map>
#include <map>
#include <unordered_map>
#include <iostream>
#include <set>
#include <hash_set>
#include <unordered_set>
#include <iterator>


#include "gif_lib.h"
#include "GifPlayer.h"
#include "EventBus.h"
#include "LogUtil.h"
#include "AssetUtil.h"

#define  MODULE_NAME "module_gif"

using namespace ::std;
static map<jobject, GifPlayer *> gifmap;

/**
 * 来自asset
 */
extern "C" JNIEXPORT void JNICALL
setDataSource(JNIEnv *env, jobject jgifplayer, jstring jassetName, jobject jassetManager,
              jobject jbitmap) {
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) {
        AndroidBitmapInfo bitmapInfo;
        AndroidBitmap_getInfo(env, jbitmap, &bitmapInfo);
        char *assetName = const_cast<char *>(env->GetStringUTFChars(jassetName, 0));
        AAssetManager *assetManager = AAssetManager_fromJava(env, jassetManager);
        gifPlayer = AssetsGifPlayer::createAndBind(assetName, assetManager, &bitmapInfo,
                                                   jgifplayer);
        gifmap[jgifplayer] = gifPlayer;
    }
}
/**
 *  来自网络的地址，来自sdcard的地址
 */
extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_setDataSource(
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

extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifWidth(JNIEnv *env, jobject jgifplayer) {
    LOGI(MODULE_NAME, "player getwidth: %d", jgifplayer);
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) {
        LOGE(MODULE_NAME, "jgifplayer must not be null");
        return 0;
    }
    return gifPlayer->getGifWidth();
}
extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifHeight(JNIEnv *env, jobject jgifplayer) {
    LOGI(MODULE_NAME, "player getheight: %d", jgifplayer);
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) {
        LOGE(MODULE_NAME, "jgifplayer must not be null");
        return 0;
    }
    return gifPlayer->getGifHeight();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_start(JNIEnv *env, jobject jgifplayer) {
    GifPlayer *gifPlayer = gifmap[jgifplayer];
    if (gifPlayer == nullptr) {
        LOGE(MODULE_NAME, "jgifplayer must not be null");
        return;
    }
    gifPlayer->start();
}


/**
 * =======================================================================
 * jni init
 * =======================================================================
 */
JNINativeMethod method[] = {
        {"setDataSource", "(Ljava/lang/String;Landroid/content/res/AssetManager;Landroid/graphics/Bitmap;)V", (void *) setDataSource},
//        {"setDataSource", "(Ljava/lang/String;Landroid/graphics/Bitmap;)V", (void *) setDataSource},
};

jint registerNativeMethod(JNIEnv *env) {
    jclass cl = env->FindClass("com/hawksjamesf/image/GifPlayer");
    if ((env->RegisterNatives(cl, method, sizeof(method) / sizeof(method[0]))) < 0) {
        return -1;
    }
    return 0;
}

jint unRegisterNativeMethod(JNIEnv *env) {
    jclass cl = env->FindClass("com/hawksjamesf/image/GifPlayer");
    env->UnregisterNatives(cl);
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
//    ATrace_beginSection("JNI_OnLoad");
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_OK) {
        registerNativeMethod(env);
        return JNI_VERSION_1_6;
    } else if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) == JNI_OK) {
        registerNativeMethod(env);
        return JNI_VERSION_1_4;
    }
//    ATrace_endSection();
    return JNI_ERR;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_OK) {
        unRegisterNativeMethod(env);
    } else if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) == JNI_OK) {
        unRegisterNativeMethod(env);
    }
}