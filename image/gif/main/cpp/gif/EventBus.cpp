//
// Created by hawks.jamesf on 1/11/20.
//

#include "EventBus.h"
#include <string>

/**
 * 任何的静态成员都要在这里初始化
 *static start
 */
ReflectUtil *EventBus::reflectUtil = 0;
map<jobject, jclass> EventBus::bitmapListenerMap;
string EventBus::onBitmapAvailable_sig = "(Landroid/graphics/Bitmap;II)V";
string EventBus::onBitmapAvailable_methodName = "onBitmapAvailable";
string EventBus::onBitmapSizeChanged_sig = "(Landroid/graphics/Bitmap;II)V";
string EventBus::onBitmapSizeChanged_methodName = "onBitmapSizeChanged";
string EventBus::onBitmapDestroyed_sig = "(Landroid/graphics/Bitmap;)Z";
string EventBus::onBitmapDestroyed_methodName = "onBitmapDestroyed";
string EventBus::onBitmapUpdated_sig = "(Landroid/graphics/Bitmap;)V";
string EventBus::onBitmapUpdated_methodName = "onBitmapUpdated";

void EventBus::init(JNIEnv *env, jobject jbitmapListener) {
    ReflectUtil::init(env);
    reflectUtil = ReflectUtil::reflect(jbitmapListener);
    if (bitmapListenerMap[jbitmapListener] == nullptr) {
        bitmapListenerMap[jbitmapListener] = env->GetObjectClass(jbitmapListener);
    }

//    jclass bitmapListenerClass = env->GetObjectClass(jbitmapListener);
//    jmethodID  jmethodId =env->GetMethodID(bitmapListenerClass,onBitmapAvailable_methodName.c_str(),onBitmapAvailable_sig.c_str());
//    env->CallVoidMethod(jbitmapListener,jmethodId, nullptr,2,3);

}

void EventBus::sendEventBitmapAvailable() {
    reflectUtil->method(EventBus::onBitmapAvailable_methodName, EventBus::onBitmapAvailable_sig);
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
