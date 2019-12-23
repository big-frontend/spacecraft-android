#include <string>

#include <jni.h>
#include <string.h>
#include <inttypes.h>
#include <pthread.h>
#include <android/log.h>
#include <assert.h>
#include <time.h>


extern "C" JNIEXPORT jstring JNICALL Java_com_hawksjamesf_common_YPoseActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    struct  timeval Time;

    return env->NewStringUTF(hello.c_str());
}
