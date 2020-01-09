//
// Created by hawks.jamesf on 1/8/20.
//

#include "GifPlayer.h"

//GifPlayer::GifPlayer(AndroidBitmapInfo *bitmapInfo) : mBitmapInfo(bitmapInfo) {};

AssetsGifPlayer::AssetsGifPlayer(char *assetName, AAsset *aAsset) : mAsset(aAsset) {
    std::string assetNameString(assetName);
    fileName = assetNameString;
};

AssetsGifPlayer::AssetsGifPlayer(char *assetName, AAssetManager *assetManager) : mAsset(
        aasset_create(assetManager, assetName, AASSET_MODE::STREAMING)) {
    std::string assetNameString(assetName);
    fileName = assetNameString;
};

AssetsGifPlayer::~AssetsGifPlayer() {
    if (mAsset != nullptr) {
        AAsset_close(mAsset);
    }
};

off_t AssetsGifPlayer::getFileSize() {
    return AAsset_getLength(mAsset);
}

int AssetsGifPlayer::fileRead(GifFileType *gif, GifByteType *buf, int size) {
    AAsset *asset = (AAsset *) gif->UserData;
    return AAsset_read(asset, buf, (size_t) size);
};

void AssetsGifPlayer::setDataSource(char *assetName, AAsset *aAsset) {
    if (mAsset == nullptr) {
        LOGE(MODULE_NAME, "exception:asset must be not empty");
        throw "asset must be not empty";
    }
    std::string assetNameString(assetName);
    fileName = assetNameString;
    mAsset = aAsset;
    int error = -1;
    gifFileType = DGifOpen(mAsset, fileRead, &error);
//    GifFileType *gifFileType  = DGifOpenFileName(fd, &error);
//    DGifCloseFile(gifFileType, &error);
    LOGE(MODULE_NAME, "error: %s", GifErrorString(gifFileType->Error));
//        LOGE(MODULE_NAME, "error: %s", GifErrorString(error));
};

void AssetsGifPlayer::setDataSource(char *assetName, AAssetManager *assetManager) {
    setDataSource(assetName, aasset_create(assetManager, assetName, AASSET_MODE::STREAMING));
};

void AssetsGifPlayer::start() {}

void AssetsGifPlayer::pause() {}

void AssetsGifPlayer::stop() {}

