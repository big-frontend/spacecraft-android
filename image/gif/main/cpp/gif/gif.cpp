//
// Created by hawks.jamesf on 12/31/19.
//
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/trace.h>
#include <zlib.h>
#include "logutil.h"


#define MODULE_NAME  "gif"
//void openAssets() {
//    ATrace_beginSection("openAssets");
//    AAssetDir *assetDir = AAssetManager_openDir(mgr, "");
//    const char *filename = (const char *) NULL;
//    while ((filename = AAssetDir_getNextFileName(assetDir)) != NULL) {
//        AAsset *asset = AAssetManager_open(mgr, filename, AASSET_MODE_STREAMING);
//        char buf[BUFSIZ];
//        int nb_read = 0;
//        FILE *out = fopen(filename, "w");
//        while ((nb_read = AAsset_read(asset, buf, BUFSIZ)) > 0)
//            fwrite(buf, nb_read, 1, out);
//        fclose(out);
//        AAsset_close(asset);
//    }
//    AAssetDir_close(assetDir);
//
//    ATrace_endSection();
//}
JNIEXPORT jint JNICALL JNI_Onload(JavaVM *vm, void *reserved) {}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {}


extern "C" JNIEXPORT void JNICALL
Java_com_hawksjamesf_image_GifImageView_setSource(JNIEnv *env, jobject gifImageView,
                                                  jstring assetName) {
    LOGD(MODULE_NAME,"assetName: %s",env->GetStringUTFChars(assetName,0));
}