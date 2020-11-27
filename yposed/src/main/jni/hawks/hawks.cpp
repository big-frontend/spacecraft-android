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
const char *RELEASE_SIGN = "3082033130820219a0030201020204596e28f5300d06092a864886f70d01010b05003049310b3009060355040613023836310a30080603550408130161310a30080603550407130161310a3008060355040a130161310a3008060355040b130161310a30080603550403130161301e170d3137303131373039303231365a170d3432303131313039303231365a3049310b3009060355040613023836310a30080603550408130161310a30080603550407130161310a3008060355040a130161310a3008060355040b130161310a3008060355040313016130820122300d06092a864886f70d01010105000382010f003082010a02820101009f1f3731ef4c65ccd6c4a7589eaffe813117d2112cc92279f41a22f210398baa2ddae52fd61c736b51b21c01d4a3233fd34b2b29365723bdb285bf0eddd043b7a9dd2829366974a690aa885b859a2d3fb272baf8c3ab94024f97117b6d6a68b74f2ed35daca41ef601a48c9f3393d92a4c3bb6f26152142e03290ef1d607361b0a2759479a7f0b94425bd885db49bcbb777f7dc10e7d3eff1fa4cc3080b4c8524ca6b761732100347b80d56a9bd5f6e7d503debe5c25c60194bd1c34c54f40172f2add9cf7e934aa7e64467c362d87fc91069fd29afc5e3445f609daf4fb99905c6ec17bea73252f6b264fdbb6963f5822997b36af9caccb2869a8b87a942df50203010001a321301f301d0603551d0e041604144aaa523ada5947919a2f7dbbe8cd3711b8dbb08e300d06092a864886f70d01010b050003820101008e6153b54104503b04a04d2746c35ce094688c2f05cd6f8c7edbcabb0d801a57c55f75930081294e63bbe27af5705511d8b7e5e263f0c6a9af58fd8c87fa43e22358c92ec4378ced89aa164f9770ebde94f865572bb846ce2cdf48ec5f6ddd1e4a733a5faca96244cd8e250cec6c0a16740e5bb7907db19d1db260806b4efd890c264ec59d46135b4f82077d3f233f5b349601b217f28d8392d90ae1fd5f462ec7e5889677bbd6c0054ea680b6dc9746077d8d536d7bc5a39dbb3074658c986a8ca14b6110599808d6f4532e32e179af558df1305880d97599d23eda5f25b0b82f091cfd702d187cfbdffc3f5bbbb9f17ae660683b07c566df5622d6e19462f8";
static jboolean auth = JNI_FALSE;


int i=0;
extern "C" JNIEXPORT jstring JNICALL Java_com_hawksjamesf_yposed_YPosedActivity_stringFromJNI(JNIEnv *env, jobject yposedActivity /* this */) {
    std::string hello = "Hello from C++ ";
    hello.append<int>(i,0x2E);
    ++i;
    return env->NewStringUTF(hello.c_str());
}

JNICALL jboolean get_signature(JNIEnv *env, jobject yposedActivity /* this */, jobject contextObject) {
    LOGI("get_signature0");
    jclass contextClass = env->FindClass("android/content/Context");
    jclass signatureClass = env->FindClass("android/content/pm/Signature");
    jclass packageNameClass = env->FindClass("android/content/pm/PackageManager");
    jclass packageInfoClass = env->FindClass("android/content/pm/PackageInfo");
LOGI("get_signature1");
    jmethodID getPackageManagerId = env->GetMethodID(contextClass, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jmethodID getPackageNameId = env->GetMethodID(contextClass, "getPackageName", "()Ljava/lang/String;");
    jmethodID signToStringId = env->GetMethodID(signatureClass, "toCharsString", "()Ljava/lang/String;");
    jmethodID toByteArrayMethodid = env->GetMethodID(signatureClass, "toByteArray", "()[B");
    jmethodID getPackageInfoId = env->GetMethodID(packageNameClass, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    LOGI("get_signature2");

    jobject packageManagerObject = env->CallObjectMethod(contextObject, getPackageManagerId);
    jstring packNameString = (jstring) env->CallObjectMethod(contextObject, getPackageNameId);
    jobject packageInfoObject = env->CallObjectMethod(packageManagerObject, getPackageInfoId, packNameString, 64);
    jfieldID signaturefieldID = env->GetFieldID(packageInfoClass, "signatures", "[Landroid/content/pm/Signature;");
    jobjectArray signatureArray = (jobjectArray) env->GetObjectField(packageInfoObject, signaturefieldID);
    jobject signatureObject = env->GetObjectArrayElement(signatureArray, 0);
    LOGI("get_signature3");
//    jstring signatureStr = (jstring) env->CallObjectMethod(signatureObject, signToStringId);
//    const char *signStrng = env->GetStringUTFChars(signatureStr, 0);

//    char *signatureByte = ( char*)env->CallObjectMethod(signatureObject, toByteArrayMethodid);
//     jbyteArray signatureByte = ( jbyteArray)env->CallObjectMethod(signatureObject, toByteArrayMethodid);
     jbyteArray signatureByte = env->NewByteArray(3);
    jclass YPosedActivityClass = env->FindClass("com/hawksjamesf/yposed/YPosedActivity");
//    jmethodID methodID = env->GetStaticMethodID(UtilClass, "sha1ToHexString", "([B)Ljava/lang/String;");
    jmethodID methodID = env->GetMethodID(YPosedActivityClass, "sha1ToHexString", "([B)Ljava/lang/String;");
//   jstring signatureStr=(jstring)env->CallStaticObjectMethod(UtilClass, methodID, signatureByte);
   jstring signatureStr=(jstring)env->CallObjectMethod(yposedActivity, methodID, signatureByte);
    const char *signaturechar = env->GetStringUTFChars(signatureStr, 0);
      LOGI("get_signature signhexStrng: sha1 %s",signaturechar);
     LOGI("get_signature4");
    env->DeleteLocalRef(contextClass);
    env->DeleteLocalRef(signatureClass);
    env->DeleteLocalRef(packageNameClass);
    env->DeleteLocalRef(packageInfoClass);
      return JNI_TRUE;
//    if (strcmp(signStrng, RELEASE_SIGN) == 0) {
//        env->ReleaseStringUTFChars(signatureStr, signStrng);
//        auth = JNI_TRUE;
//        LOGI("get_signature auth true:%s",auth);
//        return JNI_TRUE;
//    } else {
//        auth = JNI_FALSE;
//        LOGI("get_signature auth false:%s",auth);
//        return JNI_FALSE;
//    }
}

/*
* JNI registration.
*/
static JNINativeMethod gMethods[] = {
        /* name, signature, funcPtr */
//        {"setup",         "(ZI)Z",                                                   (void *) setup},
//        {"replaceMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", (void *) replaceMethod},
//        {"setFieldFlag",  "(Ljava/lang/reflect/Field;)V",                            (void *) setFieldFlag},
        {"getSignature",     "(Landroid/content/Context;)Z", (void *) get_signature}
};
static int registerNativeMethods(JNIEnv *env, const char *className, JNINativeMethod *gMethods, int numMethods) {
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