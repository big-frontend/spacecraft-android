//
// Created by hawks.jamesf on 1/5/20.
//

#ifndef SPACECRAFTANDROID_GIFCODEC_H
#define SPACECRAFTANDROID_GIFCODEC_H

#include <stdio.h>
#include <stdlib.h>
#include <android/trace.h>
#include <zlib.h>
#include <unistd.h>
#include "logutil.h"
#include "gif_lib.h"
#include <algorithm>
#include "assetutil.h"
#include <string>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>

#define MODULE_NAME  "gif_codec"

class GifCodec {
public:
    std::string fileName;

//    GifCodec(char *uriPath);

//    ~GifCodec() {};

    virtual GifFileType *decodingGif() = 0;

    virtual off_t getFileSize() = 0;

};

class GifCodecFromAssets : public GifCodec {
private:
    AAsset *mAsset;

//    static int fileRead(GifFileType *gif, GifByteType *buf, int size);

public:
    GifCodecFromAssets(char *assetName, AAsset *aAsset) : mAsset(aAsset) {
        std::string assetNameString(assetName);
        fileName = assetNameString;
    };

    GifCodecFromAssets(char *assetName, AAssetManager *assetManager) : mAsset(
            aasset_create(assetManager, assetName, AASSET_MODE::STREAMING)) {
        std::string assetNameString(assetName);
        fileName = assetNameString;
    };

    ~GifCodecFromAssets() {
        if (mAsset != nullptr) {
            AAsset_close(mAsset);
        }

    }


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
        LOGE(MODULE_NAME, "error: %s", GifErrorString(error));
        return gifFileType;

    }

    off_t getFileSize() {
        return AAsset_getLength(mAsset);
    }
};

class GifCodecFromUri : GifCodec {
private:
    std::string mUriPath;
public:

    GifCodecFromUri(char *uriPath) {
        std::string assetNameString(uriPath);
        mUriPath = assetNameString;
    };

    ~GifCodecFromUri();

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

    off_t getFileSize() override {
        return 0;
    }
};


#endif //SPACECRAFTANDROID_GIFCODEC_H
