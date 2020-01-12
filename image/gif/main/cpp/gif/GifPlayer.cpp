//
// Created by hawks.jamesf on 1/11/20.
//
#include "GifPlayer.h"

string GifPlayer::onBitmapAvailable_sig = "(II)V";
string GifPlayer::onBitmapAvailable_methodName = "onBitmapAvailable";
string GifPlayer::onBitmapSizeChanged_sig = "(II)V";
string GifPlayer::onBitmapSizeChanged_methodName = "onBitmapSizeChanged";
string GifPlayer::onBitmapDestroyed_sig = "()Z";
string GifPlayer::onBitmapDestroyed_methodName = "onBitmapDestroyed";
string GifPlayer::onBitmapUpdated_sig = "()V";
string GifPlayer::onBitmapUpdated_methodName = "onBitmapUpdated";

int GifPlayer::getGifHeight() {
    return gifFileType->SHeight;
};

int GifPlayer::getGifWidth() {
    return gifFileType->SWidth;
};

void GifPlayer::bindBitmap(jobject bitmap) {
    jbitmap = bitmap;
}

void GifPlayer::refreshReflectUtil(ReflectUtil *ru) {
    reflectUtil = ru;
};

