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
    AndroidBitmapInfo *bitmapInfo;
public:

    std::string fileName;

    virtual GifFileType *decodingGif() = 0;

    virtual off_t getFileSize() = 0;

    virtual void setDataSource(char *assetName, AAssetManager *assetManager) = 0;

    virtual void setDataSource(char *assetName, AAsset *aAsset) = 0;

    virtual void setDataSource(char *uriPath) = 0;

    virtual void start() = 0;

    virtual void pause() = 0;

    virtual void stop() = 0;

//    static GifPlayer *createAndBind(
//            AndroidBitmapInfo *bitmapInfo,
//            char *assetName,
//            AAssetManager *assetManager) {
//
//        GifPlayer *gifPlayer = new GifPlayer(bitmapInfo);
//        gifPlayer->setDataSource(assetName, assetManager);
//
//        return gifPlayer;
//    }

};

class AssetsGifPlayer : public GifPlayer {
private:
    AAsset *mAsset;

//    static int fileRead(GifFileType *gif, GifByteType *buf, int size);
    AssetsGifPlayer(AndroidBitmapInfo *binfo) : bitmapInfo(binfo) {};

    AssetsGifPlayer(char *assetName, AAsset *aAsset);

    AssetsGifPlayer(char *assetName, AAssetManager *assetManager);

    ~AssetsGifPlayer();


protected:
    static int fileRead(GifFileType *gif, GifByteType *buf, int size) {
        AAsset *asset = (AAsset *) gif->UserData;
        return AAsset_read(asset, buf, (size_t) size);
    };

    GifFileType *decodingGif() override {
        if (mAsset == nullptr) {
            LOGE(MODULE_NAME, "exception:asset must be not empty");
            throw "asset must be not empty";
        }
        int error = -1;
        GifFileType *gifFileType = DGifOpen(mAsset, fileRead, &error);
//    GifFileType *gifFileType  = DGifOpenFileName(fd, &error);
//    DGifCloseFile(gifFileType, &error);
        LOGE(MODULE_NAME, "error: %s", GifErrorString(gifFileType->Error));
//        LOGE(MODULE_NAME, "error: %s", GifErrorString(error));
        return gifFileType;

    }

    off_t getFileSize() override;

    void setDataSource(char *assetName, AAssetManager *assetManager) override;

    void setDataSource(char *assetName, AAsset *aAsset) override;

    void start() override;

    void pause() override;

    void stop() override;

    static GifPlayer *createAndBind(
            AndroidBitmapInfo *bitmapInfo,
            char *assetName,
            AAssetManager *assetManager);


};

class UriGifPlayer : GifPlayer {
private:
    std::string mUriPath;

    UriGifPlayer(AndroidBitmapInfo *binfo);

    UriGifPlayer(char *uriPath);

    ~UriGifPlayer();

private:

    GifFileType *decodingGif() override {
        if (mUriPath.empty()) {
            LOGE(MODULE_NAME, "exception:uri path must be not empty");
            throw "uri path must be not empty";
        }
        int error = -1;
        GifFileType *gifFileType = DGifOpenFileName(mUriPath.c_str(), &error);
//    GifFileType *gifFileType  = DGifOpenFileName(fd, &error);
//    DGifCloseFile(gifFileType, &error);
        LOGE(MODULE_NAME, "error: %s", GifErrorString(error));
        return gifFileType;

    }

    static GifPlayer *createAndBind(
            AndroidBitmapInfo *bitmapInfo,
            char *uriPath);

    off_t getFileSize() override;

    void setDataSource(char *uriPath) override;

    void start() override;

    void pause() override;

    void stop() override;
};


#endif //SPACECRAFTANDROID_GIFPLAYER_H
