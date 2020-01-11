//
// Created by hawks.jamesf on 1/11/20.
//

#include "EventBus.h"
#include <string>

/**
 * 任何的静态成员都要在这里初始化
 *static start
 */
ReflectUtil* EventBus::reflectUtil=0;
map<jobject, GifPlayer *> EventBus::bitmapListenerMap;
string EventBus::onBitmapAvailable_sig = "(Landroid/graphics/Bitmap;II)V";
string EventBus::onBitmapAvailable_methodName = "onBitmapAvailable";
string EventBus::onBitmapSizeChanged_sig = "(Landroid/graphics/Bitmap;II)V";
string EventBus::onBitmapSizeChanged_methodName = "onBitmapSizeChanged";
string EventBus::onBitmapDestroyed_sig = "(Landroid/graphics/Bitmap;)Z";
string EventBus::onBitmapDestroyed_methodName = "onBitmapDestroyed";
string EventBus::onBitmapUpdated_sig = "(Landroid/graphics/Bitmap;)V";
string EventBus::onBitmapUpdated_methodName = "onBitmapUpdated";

void EventBus::init(JNIEnv *env, jobject jbitmapListener, GifPlayer *gifPlayer) {
    ReflectUtil::init(env);
    if (bitmapListenerMap[jbitmapListener] == nullptr) {
        bitmapListenerMap[jbitmapListener] = gifPlayer;
    }
    reflectUtil = ReflectUtil::reflect(jbitmapListener);
}

void EventBus::sendEventBitmapAvailable() {
//    reflectUtil->method(EventBus::onBitmapAvailable_methodName, EventBus::onBitmapAvailable_sig);
}

void EventBus::sendEventBitmapSizeChanged() {

}

void EventBus::sendEventBitmapDestroyed() {

}

void EventBus::sendEventBitmapUpdated() {

}

EventBus::~EventBus() {

}

/**
 * 任何的静态成员都要在这里初始化
 *static end
 */
