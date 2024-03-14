#include <jni.h>
#include <time.h>
#include <android/log.h>
//#include <log.h>
#include <android/bitmap.h>
extern "C"
JNIEXPORT jstring
JNICALL
Java_com_jamesfchen_myhome_network_Crypto_getClientKey(JNIEnv *env, jclass thiz,
                                                       jstring jstr_token,
                                                       jlong cur_time) {
    int *p = nullptr;
    int a = 1 + (*p);
    __android_log_print(ANDROID_LOG_VERBOSE, "electrolytej", "The value of 1 + 1 is %d", a);
    return nullptr;
}