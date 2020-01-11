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

#include "ReflectUtil.h"


#include "gif_lib.h"

#define MODULE_NAME  "gif_player"
using namespace ::std;

class GifPlayer {
protected:
    static map<jobject, GifPlayer*> bitmapListenerMap;
    static ReflectUtil* reflectUtil;
    static string onBitmapAvailable_sig;
    static string onBitmapAvailable_methodName;
    static string onBitmapSizeChanged_sig;
    static string onBitmapSizeChanged_methodName;
    static string onBitmapDestroyed_sig;
    static string onBitmapDestroyed_methodName;
    static string onBitmapUpdated_sig;
    static string onBitmapUpdated_methodName;
    GifFileType *gifFileType;
    AndroidBitmapInfo *bitmapInfo;
    jobject bitmapListener;//是native层通知java层的观察者
public:
    std::string fileName;

    GifPlayer() {}

    GifPlayer(AndroidBitmapInfo *bitmapInfo) : bitmapInfo(bitmapInfo) {}
    GifPlayer(AndroidBitmapInfo *bitmapInfo,jobject bitmapListener) : bitmapInfo(bitmapInfo),bitmapListener(bitmapListener) {}

    ~GifPlayer() {
        DGifCloseFile(gifFileType, &gifFileType->Error);
    };

    virtual void setDataSource(char *assetName, AAssetManager *assetManager) {};

    virtual void setDataSource(char *assetName, AAsset *aAsset) {};// a normal virtual method

    virtual void setDataSource(char *uriPath) {};

    virtual void start() = 0;// a pure virtual method

    virtual void pause() = 0;

    virtual void stop() = 0;

    virtual off_t getFileSize() = 0;

    int getGifHeight();

    int getGifWidth();
};

class AssetsGifPlayer : public GifPlayer {
private:
    AAsset *mAsset;

    AssetsGifPlayer(AndroidBitmapInfo *binfo);
    AssetsGifPlayer(AndroidBitmapInfo *binfo,jobject bitmapListener): GifPlayer(binfo,bitmapListener) {};;

//    AssetsGifPlayer(char *assetName, AAsset *aAsset);
//    AssetsGifPlayer(char *assetName, AAssetManager *assetManager);

    ~AssetsGifPlayer();

public:
    static int fileRead(GifFileType *gif, GifByteType *buf, int size);

    static GifPlayer *createAndBind(
            char *assetName, AAssetManager *assetManager,
            AndroidBitmapInfo *bitmapInfo, jobject bitmapListener);

    off_t getFileSize() override;// a normal non-virtual method

    void setDataSource(char *assetName, AAssetManager *assetManager) override;

    void setDataSource(char *assetName, AAsset *aAsset) override;

    void start() override;

    void pause() override;

    void stop() override;

};

class UriGifPlayer : public GifPlayer {
private:
    std::string mUriPath;

    UriGifPlayer(AndroidBitmapInfo *binfo) : GifPlayer(binfo) {};
    UriGifPlayer(AndroidBitmapInfo *binfo,jobject bitmapListener) : GifPlayer(binfo,bitmapListener) {};

//    UriGifPlayer(char *uriPath);

    ~UriGifPlayer();

public:
    static GifPlayer *createAndBind(AndroidBitmapInfo *bitmapInfo, char *uriPath);

    off_t getFileSize() override;

    void setDataSource(char *uriPath) override;

    void start() override;

    void pause() override;

    void stop() override;
};

#endif //SPACECRAFTANDROID_GIFPLAYER_H
