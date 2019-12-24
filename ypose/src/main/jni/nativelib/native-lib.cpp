#include <string>

#include <jni.h>
#include <string.h>
#include <inttypes.h>
#include <pthread.h>
#include <android/log.h>
#include <assert.h>

static const char *kTAG = "ypose_log";
#define LOGI(...) (void)__android_log_print(ANDROID_LOG_INFO,kTAG,__VA_ARGS__)

// void* (*__start_routine)(void*)
void *update(void *context) {}

extern "C" JNIEXPORT jstring JNICALL Java_com_hawksjamesf_common_YPoseActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    pthread_t threadInfo_;
    pthread_attr_t threadAttr_;
    pthread_attr_init(&threadAttr_);
    pthread_attr_setdetachstate(&threadAttr_, PTHREAD_CREATE_DETACHED);
//    pthread_mutex_init(&g_ctx)
//    int result = pthread_create(&threadInfo_, &threadAttr_, update,);
    int result = 0;
    assert(result == 0);
    pthread_attr_destroy(&threadAttr_);
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


