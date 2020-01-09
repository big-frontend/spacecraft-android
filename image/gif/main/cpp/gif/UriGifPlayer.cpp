//
// Created by hawks.jamesf on 1/5/20.
//

#include "GifPlayer.h"

UriGifPlayer::UriGifPlayer(char *uriPath) {
    std::string assetNameString(uriPath);
    mUriPath = assetNameString;
}
UriGifPlayer::UriGifPlayer(AndroidBitmapInfo *binfo):bitmapInfo(bitmapInfo){};

UriGifPlayer::~UriGifPlayer() {

}

void UriGifPlayer::setDataSource(char *uriPath) {
    std::string assetNameString(uriPath);
    mUriPath = assetNameString;
}

static GifPlayer *UriGifPlayer::createAndBind(
        AndroidBitmapInfo *bitmapInfo,
        char* uriPath) {
    GifPlayer *gifPlayer = new UriGifPlayer(bitmapInfo);
    gifPlayer->setDataSource(uriPath);
    return gifPlayer;
}

off_t UriGifPlayer::getFileSize() {
    return 0;
}

void UriGifPlayer::start() {}

void UriGifPlayer::pause() {}

void UriGifPlayer::stop() {}
