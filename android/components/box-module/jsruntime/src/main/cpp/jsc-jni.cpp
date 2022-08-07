
#include <stdio.h>

#include <jni.h>

#include <android/log.h>
#define LOG_TAG "hellojsc"
#define ALOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define ALOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#include <JavaScriptCore/JSContextRef.h>
#include <JavaScriptCore/JSObjectRef.h>
#include <JavaScriptCore/JSValueRef.h>
#include <JavaScriptCore/JSStringRef.h>
#include <fbjni/fbjni.h>
JSValueRef ObjectCallAsFunctionCallback(JSContextRef ctx, JSObjectRef function, JSObjectRef thisObject, size_t argumentCount, const JSValueRef arguments[], JSValueRef* exception) {
    ALOGI("hello world jsc");
    return JSValueMakeUndefined(ctx);
}

static void testJSC() {
    JSContextGroupRef contextGroup = JSContextGroupCreate();
    JSGlobalContextRef globalContext = JSGlobalContextCreateInGroup(contextGroup, NULL);
    JSObjectRef globalObject = JSContextGetGlobalObject(globalContext);
    JSObjectRef functionObject = JSObjectMakeFunctionWithCallback(globalContext, JSStringCreateWithUTF8CString("log"), &ObjectCallAsFunctionCallback);
    JSObjectSetProperty(globalContext, globalObject, JSStringCreateWithUTF8CString("log"), functionObject, kJSPropertyAttributeNone, NULL);
    JSEvaluateScript(globalContext, JSStringCreateWithUTF8CString("var obj=0;log()"), NULL, NULL, 1, NULL);
}
static jint
add(JNIEnv *env, jobject thiz, jint a, jint b) {
int result = a + b;
    ALOGI("%d + %d = %d", a, b, result);
    testJSC();

    return result;
}

static const char *classPathName = "com/example/hellojsc/Native";

static JNINativeMethod methods[] = {
  {"add", "(II)I", (void*)add },
};

/*
 * Register several native methods for one class.
 */
static int registerNativeMethods(JNIEnv* env, const char* className,
    JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;

    clazz = env->FindClass(className);
    if (clazz == NULL) {
        ALOGE("Native registration unable to find class '%s'", className);
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        ALOGE("RegisterNatives failed for '%s'", className);
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

/*
 * Register native methods for all classes we know about.
 *
 * returns JNI_TRUE on success.
 */
static int registerNatives(JNIEnv* env)
{
  if (!registerNativeMethods(env, classPathName,
                 methods, sizeof(methods) / sizeof(methods[0]))) {
    return JNI_FALSE;
  }

  return JNI_TRUE;
}


// ----------------------------------------------------------------------------

/*
 * This is called by the VM when the shared library is first loaded.
 */
 
typedef union {
    JNIEnv* env;
    void* venv;
} UnionJNIEnvToVoid;

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    UnionJNIEnvToVoid uenv;
    uenv.venv = NULL;
    jint result = -1;
    JNIEnv* env = NULL;
    
    ALOGI("JNI_OnLoad");

    if (vm->GetEnv(&uenv.venv, JNI_VERSION_1_4) != JNI_OK) {
        ALOGE("ERROR: GetEnv failed");
        goto bail;
    }
    env = uenv.env;

    if (registerNatives(env) != JNI_TRUE) {
        ALOGE("ERROR: registerNatives failed");
        goto bail;
    }
    
    result = JNI_VERSION_1_4;
    
bail:
    return result;
}
