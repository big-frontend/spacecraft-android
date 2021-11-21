//
// Created by hawks.jamesf on 1/12/20.
//

#ifndef SPACECRAFTANDROID_CONSTANTS_H
#define SPACECRAFTANDROID_CONSTANTS_H

string onBitmapAvailable_sig = "(II)V";
string onBitmapAvailable_methodName = "onBitmapAvailable";
string onBitmapSizeChanged_sig = "(II)V";
string onBitmapSizeChanged_methodName = "onBitmapSizeChanged";
string onBitmapDestroyed_sig = "()Z";
string onBitmapDestroyed_methodName = "onBitmapDestroyed";
string onBitmapUpdated_sig = "()V";
string onBitmapUpdated_methodName = "onBitmapUpdated";

//GifPlayer.java
static struct {
    jclass clazz;

    jmethodID onBitmapAvailable;
    jmethodID onBitmapSizeChanged;
    jmethodID onBitmapDestroyed;
    jmethodID onBitmapUpdated;
} gGifPlayerClassInfo;


#endif //SPACECRAFTANDROID_CONSTANTS_H
