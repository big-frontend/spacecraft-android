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

#include <android/bitmap.h>

JNIEXPORT jint JNICALL JNI_Onload(JavaVM *vm, void *reserved) {
//    ATrace_beginSection("JNI_Onload");
//    ATrace_endSection();
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {}

/**
 * 来自asset
 */
extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_setSource(JNIEnv *env, jobject gifplayer,
                                               jstring assetNameFromJava,
                                               jobject assetManagerFromJava) {
    char *assetName = const_cast<char *>(env->GetStringUTFChars(assetNameFromJava, 0));
    AAssetManager *assetManager = AAssetManager_fromJava(env, assetManagerFromJava);
    GifCodec *gifCodec = new GifCodecFromAssets(assetName, assetManager);
    GifFileType *gifFileType = gifCodec->decodingGif();
//    LOGD(MODULE_NAME, "file name: %s, file size: %d bytes", gifCodec->fileName,gifCodec->getFileSize());
    LOGD(MODULE_NAME,
         "width: %d,height: %d,left %d,top:%d,right:%d,bottom:%d \ncolor resloution: %d, background color: %d,AspectByte %d",
         gifFileType->SWidth, gifFileType->SHeight, gifFileType->Image.Left, gifFileType->Image.Top,
         gifFileType->Image.Width, gifFileType->Image.Height, gifFileType->SColorResolution,
         gifFileType->SBackGroundColor,
         gifFileType->AspectByte);

    DGifSlurp(gifFileType);
    LOGD(MODULE_NAME,
         "width: %d,height: %d,left %d,top:%d,right:%d,bottom:%d \ncolor resloution: %d, background color: %d,AspectByte %d",
         gifFileType->SWidth, gifFileType->SHeight, gifFileType->Image.Left, gifFileType->Image.Top,
         gifFileType->Image.Width, gifFileType->Image.Height, gifFileType->SColorResolution,
         gifFileType->SBackGroundColor,
         gifFileType->AspectByte);

//    for (int i = 0; i < gifFileType->SColorResolution; ++i) {
//        for (int j = 0; j < gifFileType->SColorMap[i].ColorCount; ++j) {
//            LOGD(MODULE_NAME,"SColorMap--->i/j: %d/%d,ColorCount: %d,BitsPerPixel: %d,SortFlag: %d, rgb: %d %d %d",
//                 i, j,
//                 gifFileType->SColorMap[i].ColorCount,
//                 gifFileType->SColorMap[i].BitsPerPixel,
//                 gifFileType->SColorMap[i].SortFlag,
//                 gifFileType->SColorMap[i].Colors[j].Red,
//                 gifFileType->SColorMap[i].Colors[j].Green,
//                 gifFileType->SColorMap[i].Colors[j].Blue
//            );
//        }
//    }
    for (int i = 0; i < gifFileType->ImageCount; ++i) {
        for (int j = 0; j < gifFileType->SavedImages[i].ExtensionBlockCount; ++j) {
            LOGD(MODULE_NAME,
                 "SavedImages--->i/j: %d/%d, left: %d ,top: %d ,right %d,bottom: %d,\nExtensionBlockCount: %d,ByteCount : %d ,Bytes: %d,Function: %d",
                 i, j,
                 gifFileType->SavedImages[i].ImageDesc.Left,
                 gifFileType->SavedImages[i].ImageDesc.Top,
                 gifFileType->SavedImages[i].ImageDesc.Width,
                 gifFileType->SavedImages[i].ImageDesc.Height,
                 gifFileType->SavedImages[i].ExtensionBlockCount,
                 gifFileType->SavedImages[i].ExtensionBlocks[j].ByteCount,
                 gifFileType->SavedImages[i].ExtensionBlocks[j].Bytes,
                 gifFileType->SavedImages[i].ExtensionBlocks[j].Function
            );
        }

    }
    for (int i = 0; i < gifFileType->SColorMap->ColorCount; ++i) {
        LOGD(MODULE_NAME, "SColorMap--->index: %d,color rgb: %d , %d , %d",
             i,
             gifFileType->SColorMap->Colors[i].Red,
             gifFileType->SColorMap->Colors[i].Green,
             gifFileType->SColorMap->Colors[i].Blue
        );
    }

    for (int i = 0; i < gifFileType->ExtensionBlockCount; ++i) {
        LOGD(MODULE_NAME, "ExtensionBlock--->index: %d,ByteCount : %d ,Bytes: %d,Function: %d ",
             i,
             gifFileType->ExtensionBlocks[i].ByteCount,
             gifFileType->ExtensionBlocks[i].Bytes,
             gifFileType->ExtensionBlocks[i].Function
        );
    }

//    off_t start = 0, length = 0;
//    int fd = AAsset_openFileDescriptor(aAsset, &start, &length);
//    lseek(fd, start, SEEK_CUR);
//    decodingGif(fd, false);
    DGifCloseFile(gifFileType, &gifFileType->Error);
    delete gifCodec;
}
/**
 *  来自网络的地址，来自sdcard的地址
 */
extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_setSource1(JNIEnv *env, jobject gifplayer,
                                                jstring uriPathFromJava) {
    char *uriPath = const_cast<char *>(env->GetStringUTFChars(uriPathFromJava, 0));
//    GifCodec *gifCodec = new GifCodec(uriPath);
//    GifFileType *gifFileType = gifCodec->decodingGif();
//    LOGD(MODULE_NAME, "file name: %s, file size %d bytes", uriPath,
//         gifFileType->ExtensionBlocks->ByteCount);
//    LOGD(MODULE_NAME, "screen-->width: %d,height: %d", gifFileType->SWidth, gifFileType->SHeight);
}

// function contents
static int jniGetFDFromFileDescriptor(JNIEnv * env, jobject fileDescriptor) {
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
Java_com_hawksjamesf_image_GifPlayer_setDataSource(JNIEnv *env, jobject gifplayer,jobject fileDescriptor, jlong offset, jlong length) {
    int fd=jniGetFDFromFileDescriptor(env,fileDescriptor);
}

extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifWidth(JNIEnv *env, jobject gifplayer) {}
extern "C" JNIEXPORT jint JNICALL
Java_com_hawksjamesf_image_GifPlayer_getGifHeight(JNIEnv *env, jobject gifplayer) {}

extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_setBitmap(JNIEnv *env, jobject thiz,jobject jbitmap) {
    AndroidBitmapInfo bitmapInfo;
    void *pixels;
    AndroidBitmap_getInfo(env, jbitmap, &bitmapInfo);
    AndroidBitmap_lockPixels(env, jbitmap, &pixels);
    bitmapInfo.flags;
    bitmapInfo.format;
    bitmapInfo.stride;
    bitmapInfo.width;
    bitmapInfo.height;
    AndroidBitmap_unlockPixels(env, jbitmap);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifPlayer_start(JNIEnv *env, jobject thiz) {

}

