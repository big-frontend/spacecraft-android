//
// Created by hawks.jamesf on 1/11/20.
//
#include "GifPlayer.h"

ReflectUtil* GifPlayer::reflectUtil=0;
string GifPlayer::onBitmapAvailable_sig = "(Landroid/graphics/Bitmap;II)V";
string GifPlayer::onBitmapAvailable_methodName = "onBitmapAvailable";
string GifPlayer::onBitmapSizeChanged_sig = "(Landroid/graphics/Bitmap;II)V";
string GifPlayer::onBitmapSizeChanged_methodName = "onBitmapSizeChanged";
string GifPlayer::onBitmapDestroyed_sig = "(Landroid/graphics/Bitmap;)Z";
string GifPlayer::onBitmapDestroyed_methodName = "onBitmapDestroyed";
string GifPlayer::onBitmapUpdated_sig = "(Landroid/graphics/Bitmap;)V";
string GifPlayer::onBitmapUpdated_methodName = "onBitmapUpdated";

int GifPlayer::getGifHeight() {
    return gifFileType->SHeight;
};

int GifPlayer::getGifWidth() {
    return gifFileType->SWidth;
};

