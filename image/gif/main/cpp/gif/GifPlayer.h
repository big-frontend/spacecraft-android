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
    AndroidBitmapInfo *bitmapInfo;
public:
    std::string fileName;

    GifPlayer(AndroidBitmapInfo *bitmapInfo) : bitmapInfo(bitmapInfo) {}

    GifPlayer() {}
    ~GifPlayer(){
        DGifCloseFile(gifFileType, &gifFileType->Error);
    };

    virtual void setDataSource(char *assetName, AAssetManager *assetManager) {};

    virtual void setDataSource(char *assetName, AAsset *aAsset) {};// a normal virtual method

    virtual void setDataSource(char *uriPath) {};

    virtual void start() = 0;// a pure virtual method

    virtual void pause() = 0;

    virtual void stop() = 0;

    virtual off_t getFileSize() = 0;
    virtual  int  getGifHeight()=0;
    virtual  int  getGifWidth()=0;
};

class AssetsGifPlayer : public GifPlayer {
private:
    AAsset *mAsset;

    AssetsGifPlayer(AndroidBitmapInfo *binfo);

    AssetsGifPlayer(char *assetName, AAsset *aAsset);

    AssetsGifPlayer(char *assetName, AAssetManager *assetManager);

    ~AssetsGifPlayer();

public:
    static int fileRead(GifFileType *gif, GifByteType *buf, int size);

    static GifPlayer *createAndBind(AndroidBitmapInfo *bitmapInfo,
                                    char *assetName, AAssetManager *assetManager);

    off_t getFileSize() override;// a normal non-virtual method

    void setDataSource(char *assetName, AAssetManager *assetManager) override;

    void setDataSource(char *assetName, AAsset *aAsset) override;

    void start() override;

    void pause() override;

    void stop() override;

    int getGifHeight() override;

    int getGifWidth() override;
};

class UriGifPlayer : public GifPlayer {
private:
    std::string mUriPath;

    UriGifPlayer(AndroidBitmapInfo *binfo) : GifPlayer(binfo) {};

    UriGifPlayer(char *uriPath);

    ~UriGifPlayer();

public:
    static GifPlayer *createAndBind(AndroidBitmapInfo *bitmapInfo, char *uriPath);

    off_t getFileSize() override;

    void setDataSource(char *uriPath) override;

    void start() override;

    void pause() override;

    void stop() override;

    int getGifHeight() override;

    int getGifWidth() override;

};

#endif //SPACECRAFTANDROID_GIFPLAYER_H
