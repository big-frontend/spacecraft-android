//
// Created by hawks.jamesf on 1/5/20.
//

#include "GifPlayer.h"

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

void AssetsGifPlayer::setDataSource(char *assetName, AAsset *aAsset) {
    std::string assetNameString(assetName);
    fileName = assetNameString;
    mAsset = aAsset;
};

void AssetsGifPlayer::setDataSource(char *assetName, AAssetManager *assetManager) {
    std::string assetNameString(assetName);
    fileName = assetNameString;
    mAsset = aasset_create(assetManager, assetName, AASSET_MODE::STREAMING);
};

static GifPlayer *AssetsGifPlayer::createAndBind(
        AndroidBitmapInfo *bitmapInfo,
        char *assetName,
        AAssetManager *assetManager) {
    GifPlayer *gifPlayer = new AssetsGifPlayer(bitmapInfo);
    gifPlayer->setDataSource(assetName, assetManager);
    return gifPlayer;
}

void AssetsGifPlayer::start() {}

void AssetsGifPlayer::pause() {}

void AssetsGifPlayer::stop() {}

