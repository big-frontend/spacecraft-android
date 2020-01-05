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
#include "GifCodec.h"

#define  MODULE_NAME "module_gif"

JNIEXPORT jint JNICALL JNI_Onload(JavaVM *vm, void *reserved) {
    ATrace_beginSection("JNI_Onload");
    ATrace_endSection();
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {}

/**
 * 来自asset
 */
extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifImageView_setSource(JNIEnv *env, jobject gifImageView,
                                                  jstring assetNameFromJava,
                                                  jobject assetManagerFromJava) {
    char *assetName = const_cast<char *>(env->GetStringUTFChars(assetNameFromJava, 0));
    AAssetManager *assetManager = AAssetManager_fromJava(env, assetManagerFromJava);
    GifCodec gifCodec2 = GifCodecFromAssets(assetName, assetManager);
    gifCodec2.fileName;
    GifCodec *gifCodec = new GifCodecFromAssets(assetName, assetManager);
    gifCodec->fileName;
    GifFileType *gifFileType = gifCodec->decodingGif();
    LOGD(MODULE_NAME, "filename: %s, fileLength %d bytes", assetName, gifCodec->getFileSize());
    LOGD(MODULE_NAME, "screen-->width: %d,height: %d", gifFileType->SWidth, gifFileType->SHeight);
//    off_t start = 0, length = 0;
//    int fd = AAsset_openFileDescriptor(aAsset, &start, &length);
//    lseek(fd, start, SEEK_CUR);
//    decodingGif(fd, false);
    delete gifCodec;
}
/**
 *  来自网络的地址，来自sdcard的地址
 */
extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifImageView_setSource1(JNIEnv *env, jobject gifImageView,
                                                   jstring uriPathFromJava) {
    char *uriPath = const_cast<char *>(env->GetStringUTFChars(uriPathFromJava, 0));
//    GifCodec *gifCodec = new GifCodec(uriPath);
//    GifFileType *gifFileType = gifCodec->decodingGif();
//    LOGD(MODULE_NAME, "filename: %s, fileLength %d bytes", uriPath,
//         gifFileType->ExtensionBlocks->ByteCount);
//    LOGD(MODULE_NAME, "screen-->width: %d,height: %d", gifFileType->SWidth, gifFileType->SHeight);
}

