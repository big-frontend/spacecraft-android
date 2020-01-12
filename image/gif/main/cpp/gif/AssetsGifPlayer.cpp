//
// Created by hawks.jamesf on 1/8/20.
//

#include <LogUtil.h>
#include <AssetUtil.h>
#include "GifPlayer.h"

// static start

int AssetsGifPlayer::fileRead(GifFileType *gif, GifByteType *buf, int size) {
    AAsset *asset = (AAsset *) gif->UserData;
    return AAsset_read(asset, buf, (size_t) size);
};

GifPlayer *AssetsGifPlayer::create(JNIEnv *env, char *assetName, AAssetManager *assetManager) {
//    AssetsGifPlayer *gifPlayer=&assetsGifPlayer;
    AssetsGifPlayer *gifPlayer = new AssetsGifPlayer(env);
    gifPlayer->setDataSource(assetName, assetManager);
    return gifPlayer;
}

// static end

AssetsGifPlayer::~AssetsGifPlayer() {
    if (mAsset != nullptr) {
        AAsset_close(mAsset);
    }
};

off_t AssetsGifPlayer::getFileSize() {
    return AAsset_getLength(mAsset);
}

void AssetsGifPlayer::setDataSource(char *assetName, AAssetManager *assetManager) {
    setDataSource(assetName, aasset_create(assetManager, assetName, AASSET_MODE::STREAMING));
};

void logGifFileType(GifFileType *gifFileType);

void AssetsGifPlayer::setDataSource(char *assetName, AAsset *aAsset) {
    if (aAsset == nullptr) {
        LOGE(MODULE_NAME, "exception:asset must be not empty");
        throw "asset must be not empty";
    }
    std::string assetNameString(assetName);
    fileName = assetNameString;
    mAsset = aAsset;
    int error = -1;
    gifFileType = DGifOpen(mAsset, fileRead, &error);
    DGifSlurp(gifFileType);
    logGifFileType(gifFileType);
//    GifFileType *gifFileType  = DGifOpenFileName(fd, &error);
//    DGifCloseFile(gifFileType, &error);
//    LOGE(MODULE_NAME, "error: %s", GifErrorString(gifFileType->Error));
    LOGE(MODULE_NAME, "error: %s", GifErrorString(error));
};

void retZFunc(jboolean result) {
    LOGE(MODULE_NAME, "retZFunc: %d", result);
};

void AssetsGifPlayer::start() {
//    AndroidBitmapInfo info;
//    AndroidBitmap_getInfo(jniEnv,jbitmap,&info);
//    void *pixels;
//    AndroidBitmap_lockPixels(jniEnv, jbitmap, &pixels);
//    info.flags;
//    info.format;
//    info.stride;
//    info.width;
//    info.height;
//    AndroidBitmap_unlockPixels(jniEnv, jbitmap);

    reflectUtil->method(false, onBitmapAvailable_methodName, onBitmapAvailable_sig, 1, 3, 4);
    reflectUtil->method(false, onBitmapSizeChanged_methodName,
                        onBitmapSizeChanged_sig, jbitmap, getGifWidth(),
                        getGifHeight());
    reflectUtil->method(false, onBitmapDestroyed_methodName,
                        onBitmapDestroyed_sig, retZFunc, jbitmap);
    reflectUtil->method(false, onBitmapUpdated_methodName,
                        onBitmapUpdated_sig, jbitmap);
}

void AssetsGifPlayer::pause() {}

void AssetsGifPlayer::stop() {}

void logGifFileType(GifFileType *gifFileType) {
    //    LOGD(MODULE_NAME, "file name: %s, file size: %d bytes", gifCodec->fileName,gifCodec->getFileSize());
    LOGD(MODULE_NAME,
         "width: %d,height: %d,left %d,top:%d,right:%d,bottom:%d \ncolor resloution: %d, background color: %d,AspectByte %d",
         gifFileType->SWidth, gifFileType->SHeight, gifFileType->Image.Left, gifFileType->Image.Top,
         gifFileType->Image.Width, gifFileType->Image.Height, gifFileType->SColorResolution,
         gifFileType->SBackGroundColor,
         gifFileType->AspectByte);

    DGifSlurp(gifFileType);
    LOGD(MODULE_NAME,
         "width: %d,height: %d,left %d,top:%d,right:%d,bottom:%d \ncolor resloution: %d, background color: %d,AspectByte %d",
         gifFileType->SWidth, gifFileType->SHeight, gifFileType->Image.Left, gifFileType->Image.Top,
         gifFileType->Image.Width, gifFileType->Image.Height, gifFileType->SColorResolution,
         gifFileType->SBackGroundColor,
         gifFileType->AspectByte);

//    for (int i = 0; i < gifFileType->SColorResolution; ++i) {
//        for (int j = 0; j < gifFileType->SColorMap[i].ColorCount; ++j) {
//            LOGD(MODULE_NAME,"SColorMap--->i/j: %d/%d,ColorCount: %d,BitsPerPixel: %d,SortFlag: %d, rgb: %d %d %d",
//                 i, j,
//                 gifFileType->SColorMap[i].ColorCount,
//                 gifFileType->SColorMap[i].BitsPerPixel,
//                 gifFileType->SColorMap[i].SortFlag,
//                 gifFileType->SColorMap[i].Colors[j].Red,
//                 gifFileType->SColorMap[i].Colors[j].Green,
//                 gifFileType->SColorMap[i].Colors[j].Blue
//            );
//        }
//    }
    for (int i = 0; i < gifFileType->ImageCount; ++i) {
        for (int j = 0; j < gifFileType->SavedImages[i].ExtensionBlockCount; ++j) {
            LOGD(MODULE_NAME,
                 "SavedImages--->i/j: %d/%d, left: %d ,top: %d ,right %d,bottom: %d,\nExtensionBlockCount: %d,ByteCount : %d ,Bytes: %d,Function: %d",
                 i, j,
                 gifFileType->SavedImages[i].ImageDesc.Left,
                 gifFileType->SavedImages[i].ImageDesc.Top,
                 gifFileType->SavedImages[i].ImageDesc.Width,
                 gifFileType->SavedImages[i].ImageDesc.Height,
                 gifFileType->SavedImages[i].ExtensionBlockCount,
                 gifFileType->SavedImages[i].ExtensionBlocks[j].ByteCount,
                 gifFileType->SavedImages[i].ExtensionBlocks[j].Bytes,
                 gifFileType->SavedImages[i].ExtensionBlocks[j].Function
            );
        }

    }
    for (int i = 0; i < gifFileType->SColorMap->ColorCount; ++i) {
        LOGD(MODULE_NAME, "SColorMap--->index: %d,color rgb: %d , %d , %d",
             i,
             gifFileType->SColorMap->Colors[i].Red,
             gifFileType->SColorMap->Colors[i].Green,
             gifFileType->SColorMap->Colors[i].Blue
        );
    }

    for (int i = 0; i < gifFileType->ExtensionBlockCount; ++i) {
        LOGD(MODULE_NAME, "ExtensionBlock--->index: %d,ByteCount : %d ,Bytes: %d,Function: %d ",
             i,
             gifFileType->ExtensionBlocks[i].ByteCount,
             gifFileType->ExtensionBlocks[i].Bytes,
             gifFileType->ExtensionBlocks[i].Function
        );
    }

//    off_t start = 0, length = 0;
//    int fd = AAsset_openFileDescriptor(aAsset, &start, &length);
//    lseek(fd, start, SEEK_CUR);
//    decodingGif(fd, false);

}

