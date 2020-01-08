//
// Created by hawks.jamesf on 1/8/20.
//

#ifndef SPACECRAFTANDROID_GIFPLAYER_H
#define SPACECRAFTANDROID_GIFPLAYER_H

#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>

class GifPlayer {
public:
    void setDataSource(char *assetName, AAssetManager *assetManager);
    void setDataSource(char *assetName,  AAsset *aAsset);
    void start();
    void pause();
    void stop();

};


#endif //SPACECRAFTANDROID_GIFPLAYER_H
