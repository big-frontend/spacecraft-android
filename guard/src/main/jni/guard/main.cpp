#include <string.h>
#include <jni.h>
#include <string.h>
#include <inttypes.h>
#include <pthread.h>
#include <android/log.h>
#include <assert.h>
#include <time.h>
#include <sched.h>
#include <cstdio>
#include <cstdlib>
#include <unistd.h>

//JNIEXPORT  JNICALL
//int main(int argc, char *argv[]) {
//
//    struct sched_param param;
//    int maxpri;
//    maxpri = sched_get_priority_max(SCHED_FIFO);
//    if (maxpri == -1) {
//        perror("sched_get_priority_max() failed");
//        exit(1);
//    }
//    param.sched_priority = maxpri;
//    //设置优先级
//    if (sched_setscheduler(getpid(), SCHED_FIFO, &param) == -1) {
//        perror("sched_setscheduler() failed");
//        exit(1);
//    }
//}

void entry(JNIEnv *env, jobject yposedActivity /* this */) {
}

/*
* JNI registration.
*/
static JNINativeMethod gMethods[] = {
        /* name, signature, funcPtr */
//        {"setup",         "(ZI)Z",                                                   (void *) setup},
//        {"replaceMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", (void *) replaceMethod},
//        {"setFieldFlag",  "(Ljava/lang/reflect/Field;)V",                            (void *) setFieldFlag},
        {"main", "()V", (void *) entry}
};

static int registerNativeMethods(JNIEnv *env, const char *className, JNINativeMethod *gMethods,
                                 int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }

    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

#define   LOG_TAG    "cjf_jni"
#define CLASS_PATH "com/hawksjamesf/yposed/YPosedActivity"
#define   NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#define   LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define   LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

static int registerNatives(JNIEnv *env) {
    return registerNativeMethods(env, CLASS_PATH, gMethods, NELEM(gMethods));
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {

    LOGI("JNI_ONLOAD");

    JNIEnv *env = NULL;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        LOGE("JNI_ONLOAD:%s", "failed");
        return JNI_ERR;
    }
    assert(env != NULL);
    if (!registerNatives(env)) {//注册
        LOGI("registerNatives -1");
        return -1;
    }
    result = JNI_VERSION_1_6;

    return result;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    __android_log_print(ANDROID_LOG_DEBUG, "cjf", "JNI_OnUnload");
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return;
    }

}