//
// Created by hawks.jamesf on 1/8/20.
//

#ifndef SPACECRAFTANDROID_GIFPLAYER_H
#define SPACECRAFTANDROID_GIFPLAYER_H

#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>
#include <android/bitmap.h>

class GifPlayer {
public:
    void setDataSource(char *assetName, AAssetManager *assetManager);

    void setDataSource(char *assetName, AAsset *aAsset);

    void start();

    void pause();

    void stop();

    static GifPlayer *
    createAndBind(AndroidBitmapInfo *bitmapInfo, char *assetName, AAssetManager *assetManager){
        GifPlayer *gifPlayer = new GifPlayer(bitmapInfo);
        gifPlayer->setDataSource(assetName, assetManager);
        return gifPlayer;
    }

private:
    AndroidBitmapInfo *mBitmapInfo;

    GifPlayer(AndroidBitmapInfo *bitmapInfo):mBitmapInfo(bitmapInfo){};

    ~GifPlayer(){};


};


#endif //SPACECRAFTANDROID_GIFPLAYER_H
