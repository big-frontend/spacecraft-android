//
// Created by hawks.jamesf on 1/5/20.
//

#ifndef SPACECRAFTANDROID_GIFPLAYER_H
#define SPACECRAFTANDROID_GIFPLAYER_H

#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <android/bitmap.h>
#include <algorithm>
#include <stdio.h>
#include <stdlib.h>
#include <android/trace.h>
#include <zlib.h>
#include <unistd.h>
#include <string>
#include <map>

#include "gif_lib.h"
#include "ReflectUtil.h"


#define MODULE_NAME  "native/gif_player"
using namespace ::std;

class GifPlayer {
private:
    AAsset *mAsset;
    std::string mUriPath;

    GifPlayer(JNIEnv *env) : jniEnv(env) {};

    ~GifPlayer();
    JNIEnv *jniEnv;
    GifFileType *gifFileType;
    jobject jbitmap;
    ReflectUtil *reflectUtil;

public:
    static int fileRead(GifFileType *gif, GifByteType *buf, int size);

    static GifPlayer *create(JNIEnv *env, char *assetName, AAssetManager *assetManager);

    static GifPlayer *create(JNIEnv *env, char *uriPath);

    std::string fileName;

    /**
     * 解析gif图片的所有信息
     * @param assetName
     * @param assetManager
     */
    void setDataSource(char *assetName, AAssetManager *assetManager);

    void setDataSource(char *assetName, AAsset *aAsset);// a normal virtual method

    void setDataSource(char *uriPath);

    void start();// a pure  method

    void pause();

    void stop();

    off_t getFileSize();

    int getGifHeight();

    int getGifWidth();

    void bindBitmap(jobject jbitmap);

    void refreshReflectUtil(ReflectUtil *reflectUtil);

};

#endif //SPACECRAFTANDROID_GIFPLAYER_H
