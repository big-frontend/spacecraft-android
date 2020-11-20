//
// Created by hawks.jamesf on 12/19/19.
//
#include <string>
#include <jni.h>
#include <string.h>
#include <inttypes.h>
#include <pthread.h>
#include <android/log.h>
#include <assert.h>
#include <time.h>
#include "include/hawks.h"

/*
* JNI registration.
*/
static JNINativeMethod gMethods[] = {
        /* name, signature, funcPtr */
//        {"setup",         "(ZI)Z",                                                   (void *) setup},
//        {"replaceMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", (void *) replaceMethod},
//        {"setFieldFlag",  "(Ljava/lang/reflect/Field;)V",                            (void *) setFieldFlag},
};

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {

    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    __android_log_print(ANDROID_LOG_DEBUG, "hawks", "JNI_OnUnload");
}
int i=0;
extern "C" JNIEXPORT jstring JNICALL Java_com_hawksjamesf_yposed_YPosedActivity_stringFromJNI(
        JNIEnv *env, jobject yposedActivity /* this */) {
    std::string hello = "Hello from C++ ";
    hello.append<int>(i,0x2E);
    ++i;
    return env->NewStringUTF(hello.c_str());
}