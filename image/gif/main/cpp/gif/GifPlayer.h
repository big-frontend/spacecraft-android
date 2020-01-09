//
// Created by hawks.jamesf on 1/5/20.
//

#ifndef SPACECRAFTANDROID_GIFPLAYER_H
#define SPACECRAFTANDROID_GIFPLAYER_H

#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>
#include <android/bitmap.h>
#include <string>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <algorithm>
#include <stdio.h>
#include <stdlib.h>
#include <android/trace.h>
#include <zlib.h>
#include <unistd.h>
#include "gif_lib.h"
#include "assetutil.h"
#include "logutil.h"

#define MODULE_NAME  "gif_player"
using namespace ::std;

class GifPlayer {
protected:

    GifFileType *gifFileType;

    virtual void setDataSource(char *assetName, AAssetManager *assetManager) = 0;

    virtual void setDataSource(char *assetName, AAsset *aAsset) = 0;

    virtual void setDataSource(char *uriPath) = 0;

    virtual void start() = 0;

    virtual void pause() = 0;

    virtual void stop() = 0;

public:

    std::string fileName;

    virtual off_t getFileSize() = 0;
};

class AssetsGifPlayer : public GifPlayer {
private:
    AAsset *mAsset;
    AndroidBitmapInfo *bitmapInfo;
//    static int fileRead(GifFileType *gif, GifByteType *buf, int size);
    AssetsGifPlayer(AndroidBitmapInfo *binfo) : bitmapInfo(binfo) {};

    AssetsGifPlayer(char *assetName, AAsset *aAsset);

    AssetsGifPlayer(char *assetName, AAssetManager *assetManager);

    ~AssetsGifPlayer();

    int fileRead(GifFileType *gif, GifByteType *buf, int size);


public:

    off_t getFileSize() override;

    void setDataSource(char *assetName, AAssetManager *assetManager) override;

    void setDataSource(char *assetName, AAsset *aAsset) override;

    void start() override;

    void pause() override;

    void stop() override;

    static GifPlayer *createAndBind(
            AndroidBitmapInfo *bitmapInfo,
            char *assetName,
            AAssetManager *assetManager) {
        GifPlayer *gifPlayer = new AssetsGifPlayer(bitmapInfo);
        gifPlayer->setDataSource(assetName, assetManager);
        return gifPlayer;
    }
};

class UriGifPlayer : GifPlayer {
private:
    std::string mUriPath;
    AndroidBitmapInfo *bitmapInfo;
    UriGifPlayer(AndroidBitmapInfo *binfo);

    UriGifPlayer(char *uriPath);

    ~UriGifPlayer();

public:

    off_t getFileSize() override;

    void setDataSource(char *uriPath) override;

    void start() override;

    void pause() override;

    void stop() override;

    static GifPlayer *createAndBind(
            AndroidBitmapInfo *bitmapInfo,
            char *uriPath) {
        GifPlayer *gifPlayer = new UriGifPlayer(bitmapInfo);
        gifPlayer->setDataSource(uriPath);
        return gifPlayer;
    }
};


#endif //SPACECRAFTANDROID_GIFPLAYER_H
