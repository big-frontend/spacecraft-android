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

#define CLASS_PATH "com/hawksjamesf/yposed/YPosedActivity"
#define   NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#define   LOG_TAG    "cjf"
#define   LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define   LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
const char *RELEASE_SIGN = "E5:1B:40:36:1E:5E:E0:FF:82:54:64:65:06:B2:0F:93:6E:D4:17:77";
static jboolean auth = JNI_FALSE;


int i = 0;
extern "C" JNIEXPORT  JNICALL
jstring Java_com_hawksjamesf_yposed_YPosedActivity_stringFromJNI(JNIEnv *env,
                                                                 jobject yposedActivity /* this */) {
    std::string hello = "Hello from C++ ";
    hello.append<int>(i, 0x2E);
    ++i;
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT JNICALL
jboolean check_sign(JNIEnv *env, jobject yposedActivity /* this */, jobject contextObject) {
    jclass contextClass = env->FindClass("android/content/Context");
    jclass signatureClass = env->FindClass("android/content/pm/Signature");
    jclass packageNameClass = env->FindClass("android/content/pm/PackageManager");
    jclass packageInfoClass = env->FindClass("android/content/pm/PackageInfo");

    jmethodID getPackageManagerId = env->GetMethodID(contextClass, "getPackageManager",
                                                     "()Landroid/content/pm/PackageManager;");
    jmethodID getPackageNameId = env->GetMethodID(contextClass, "getPackageName",
                                                  "()Ljava/lang/String;");
    jmethodID signToStringId = env->GetMethodID(signatureClass, "toCharsString",
                                                "()Ljava/lang/String;");
    jmethodID toByteArrayMethodid = env->GetMethodID(signatureClass, "toByteArray", "()[B");
    jmethodID getPackageInfoId = env->GetMethodID(packageNameClass, "getPackageInfo",
                                                  "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");

    jobject packageManagerObject = env->CallObjectMethod(contextObject, getPackageManagerId);
    jstring packNameString = (jstring) env->CallObjectMethod(contextObject, getPackageNameId);
    jobject packageInfoObject = env->CallObjectMethod(packageManagerObject, getPackageInfoId,
                                                      packNameString, 64);
    jfieldID signaturefieldID = env->GetFieldID(packageInfoClass, "signatures",
                                                "[Landroid/content/pm/Signature;");
    jobjectArray signatureArray = (jobjectArray) env->GetObjectField(packageInfoObject,
                                                                     signaturefieldID);
    jobject signatureObject = env->GetObjectArrayElement(signatureArray, 0);

//    jstring signatureStr = (jstring) env->CallObjectMethod(signatureObject, signToStringId);

    jbyteArray signatureByte = (jbyteArray) env->CallObjectMethod(signatureObject,
                                                                  toByteArrayMethodid);
//    jclass YPosedActivityClass = env->FindClass("com/hawksjamesf/yposed/YPosedActivity");
//    jmethodID methodID = env->GetMethodID(YPosedActivityClass, "sha1ToHexString","([B)Ljava/lang/String;");
//    jstring signatureStr = (jstring) env->CallObjectMethod(yposedActivity, methodID, signatureByte);

    jclass UtilClass = env->FindClass("com/hawksjamesf/yposed/Util");
    jmethodID methodID = env->GetStaticMethodID(UtilClass, "sha1ToHexString",
                                                "([B)Ljava/lang/String;");
    jstring signatureStr = (jstring) env->CallStaticObjectMethod(UtilClass, methodID,
                                                                 signatureByte);


    const char *signaturechar = env->GetStringUTFChars(signatureStr, 0);
    LOGI("get_signature signhexStrng: sha1 %s", signaturechar);
    env->DeleteLocalRef(contextClass);
    env->DeleteLocalRef(signatureClass);
    env->DeleteLocalRef(packageNameClass);
    env->DeleteLocalRef(packageInfoClass);
    if (strcmp(signaturechar, RELEASE_SIGN) == 0) {
        env->ReleaseStringUTFChars(signatureStr, signaturechar);
        auth = JNI_TRUE;
        return JNI_TRUE;
    } else {
        auth = JNI_FALSE;
        return JNI_FALSE;
    }
}

/*
* JNI registration.
*/
static JNINativeMethod gMethods[] = {
        /* name, signature, funcPtr */
//        {"setup",         "(ZI)Z",                                                   (void *) setup},
//        {"replaceMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", (void *) replaceMethod},
//        {"setFieldFlag",  "(Ljava/lang/reflect/Field;)V",                            (void *) setFieldFlag},
        {"checkSign", "(Landroid/content/Context;)Z", (void *) check_sign}
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

static int registerNatives(JNIEnv *env) {
    return registerNativeMethods(env, CLASS_PATH, gMethods, NELEM(gMethods));
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGI("JNI_ONLOAD");

    JNIEnv *env = NULL;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        LOGE("JNI_ONLOAD:%s", "failed");
        return -1;
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
}