//
// Created by hawks.jamesf on 4/18/20.
//

#include <jni.h>
#include "ReflectUtil.h"
#define NELEM(x) (sizeof(x)/sizeof((x)[0]))
#define REG_JNI(name)      { name, #name }
struct RegJNIRec {
    int (*mProc)(JNIEnv *);

    const char *mName;
};

extern int register_com_hawksjamesf_image_DisplayEventReceiver(JNIEnv *env);


static const RegJNIRec gRegJNI[] = {
        REG_JNI(register_com_hawksjamesf_image_DisplayEventReceiver)
};

jint unRegisterNativeMethod(JNIEnv *env) {
    jclass cl = env->FindClass("com/hawksjamesf/image/GifPlayer");
    env->UnregisterNatives(cl);
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
//    ATrace_beginSection("JNI_OnLoad");
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_OK) {
        ReflectUtil::init(env);
//        registerNativeMethod(env);
        register_com_hawksjamesf_image_DisplayEventReceiver(env);
        return JNI_VERSION_1_6;
    } else if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) == JNI_OK) {
        ReflectUtil::init(env);
//        registerNativeMethod(env);
        register_com_hawksjamesf_image_DisplayEventReceiver(env);
        return JNI_VERSION_1_4;
    }
//    ATrace_endSection();
    return JNI_ERR;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_OK) {
        unRegisterNativeMethod(env);
    } else if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) == JNI_OK) {
        unRegisterNativeMethod(env);
    }
}