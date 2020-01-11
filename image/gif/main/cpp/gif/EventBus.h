//
// Created by hawks.jamesf on 1/11/20.
//
#include <jni.h>
#include <map>
#include <string>
#include "ReflectUtil.h"
#include "GifPlayer.h"

using namespace ::std;


class EventBus {

private:
    static map<jobject, GifPlayer *> bitmapListenerMap;
    static ReflectUtil *reflectUtil;
    static string onBitmapAvailable_sig;
    static string onBitmapAvailable_methodName;
    static string onBitmapSizeChanged_sig;
    static string onBitmapSizeChanged_methodName;
    static string onBitmapDestroyed_sig;
    static string onBitmapDestroyed_methodName;
    static string onBitmapUpdated_sig;
    static string onBitmapUpdated_methodName;
public:
    virtual ~EventBus();

public:

    static void init(JNIEnv *env, jobject jbitmapListener, GifPlayer *gifPlayer);

    static void sendEventBitmapAvailable();

    static void sendEventBitmapSizeChanged();

    static void sendEventBitmapDestroyed();

    static void sendEventBitmapUpdated();


};