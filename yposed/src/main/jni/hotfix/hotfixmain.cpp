#include <string>
#include <jni.h>
#include <string.h>
#include <inttypes.h>
#include <pthread.h>
#include <android/log.h>
#include <assert.h>
#include <time.h>
#include "include/art_8.0r1.h"
//#include "include/art_10.h"
#include "include/HotFix.h"

/**
 *
 *  java    | jni     | 内存分配
 *  |---|---|---|---|
 *  boolean(1bit) | jboolean| 1byte  |无符号8位整型 uint8_t
 *  byte    | jbyte   | 1byte  |有符号8位整型 int8_t
 *  char    | jchar   | 2bytes |无符号16位整型 uint16_t
 *  short   | jshort  | 2bytes |有符号8位整型 int16_t
 *  int     | jint    | 4bytes |有符号32位整型 int32_t
 *  long    | jlong   | 8bytes |有符号64位整型 int64_t
 *  float   | jfloat  | 4bytes |32位浮点型    float
 *  double  | jdouble | 8bytes |64位浮点型    double
 *
 *  Object  | jobject
 *  Class   | jclass
 *  String  | jstring
 *  Object[]| jobjectArray
 *  boolean[]|jbooleanArray
 *  byte[]   |jbyteArray
 *  char[]   |jcharArray
 *  short[]  |jshortArray
 *  int[]    |jintArray
 *  long[]   |jlongArray
 *  float[]  |jfloatArray
 *  double[] |jdoubleArray
 *  void     | void
 *
 *## The Value Type
 *typedef union jvalue {
    jboolean z;
    jbyte    b;
    jchar    c;
    jshort   s;
    jint     i;
    jlong    j;
    jfloat   f;
    jdouble  d;
    jobject  l;
 * } jvalue;
 *
 *
 *
 */



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
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) return JNI_ERR;
    jclass srcNetClientclazz = env->FindClass("com/hawksjamesf/yposed/NetClient");
    art::ArtMethod *srcArtMethod = (art::ArtMethod *) env->GetMethodID(srcNetClientclazz,
                                                                       "sendRequest", "()V");

    jclass destNetClientclazz = env->FindClass("com/hawksjamesf/yposedplugin/NetClient_sendRequest");
    art::ArtMethod *destArtMethod = (art::ArtMethod *) env->GetMethodID(destNetClientclazz,
                                                                        "sendRequest", "()V");
//    srcArtMethod->declaring_class_ = destArtMethod->declaring_class_;
    srcArtMethod->access_flags_ = destArtMethod->access_flags_ | 0x0001;
//    srcArtMethod->ptr_sized_fields_.dex_cache_resolved_methods_=destArtMethod->ptr_sized_fields_.dex_cache_resolved_methods_;
    srcArtMethod->dex_code_item_offset_ = destArtMethod->dex_code_item_offset_;
    srcArtMethod->method_index_ = destArtMethod->method_index_;
    srcArtMethod->dex_method_index_ = destArtMethod->dex_method_index_;
    __android_log_print(ANDROID_LOG_DEBUG, "hotfix", "JNI_OnLoad ---> src:%d dest:%d ",
                        srcArtMethod->declaring_class_, destArtMethod->declaring_class_);
//    throw "Division by zero condition!";
//    HotFix *hotFix= nullptr;
//    __android_log_print(ANDROID_LOG_DEBUG, "hotfix", "JNI_OnLoad %d", hotFix->c);
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    __android_log_print(ANDROID_LOG_DEBUG, "hotfix", "JNI_OnUnload");
}


extern "C" JNIEXPORT jstring JNICALL Java_com_hawksjamesf_yposed_YPosedActivity_stringFromJNI(
        JNIEnv *env, jobject ypostActivity /* this */) {
    std::string hello = "Hello from C++";
    jclass clz = env->FindClass("com/hawksjamesf/yposed/YPosedActivity");
    jobject gloablRef = env->NewGlobalRef(ypostActivity);
    jmethodID stringFromJavaFun = env->GetMethodID(clz, "stringFromJava", "()Ljava/lang/String;");
    jstring result = static_cast<jstring>(env->CallObjectMethod(ypostActivity, stringFromJavaFun));
//    env->RegisterNatives()
    struct timeval Time;

    return env->NewStringUTF(hello.c_str());
}
