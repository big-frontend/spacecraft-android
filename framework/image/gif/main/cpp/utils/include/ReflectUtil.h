//
// Created by hawks.jamesf on 1/11/20.
//

#ifndef SPACECRAFTANDROID_REFLECTUTIL_H
#define SPACECRAFTANDROID_REFLECTUTIL_H

#include <jni.h>
#include <string>

#include <stdarg.h>

using namespace ::std;

typedef void (*RetZFunc)(jboolean);

typedef void (*RetBFunc)(jbyte);

typedef void (*RetCFunc)(jchar);

typedef void (*RetSFunc)(jshort);

typedef void (*RetIFunc)(jint);

typedef void (*RetJFunc)(jlong);

typedef void (*RetFFunc)(jfloat);

typedef void (*RetDFunc)(jdouble);

typedef void (*RetLFunc)(jobject);

class ReflectUtil {
private:
    static JNIEnv *env;
    jclass type;
    jobject object;

    ReflectUtil(jclass type) : ReflectUtil(type, type) {};

    ReflectUtil(jclass type, jobject object) : type(type), object(object) {};
public:
    string boolType = "Z";
    string boolArrayType = "[Z";
    string byteType = "B";
    string byteArrayType = "[B";
    string charType = "C";
    string charArrayType = "[C";
    string shortType = "S";
    string shortArrayType = "[S";
    string intType = "I";
    string intArrayType = "[I";
    string longType = "J";
    string longArrayType = "[J";
    string floatType = "F";
    string floatArrayType = "[F";
    string doubleType = "D";
    string doubleArrayType = "[D";
//    string objectType="L";
    string stringType = "Ljava/lang/String;";
    string classType = "Ljava/lang/Class;";


//    jfieldID getField(string name);
//    jfieldID getAccessibleField(string name);


public:
    static void init(JNIEnv *jniEnv) { env = jniEnv; };

    static ReflectUtil reflect(jclass type);

    static ReflectUtil *reflect(jobject jobject);

    static ReflectUtil reflect(string classname);

    static ReflectUtil reflect(string classname, jobject classloader);

    static jclass forName(string classname);

    static jclass forName(string classname, jobject classloader);

    ReflectUtil newInstance() { return newInstance(0); };

    ReflectUtil newInstance(...);

    ReflectUtil getArgsType(...);

    ReflectUtil *field(string name, bool isStatic);

    ReflectUtil *field(string name, jboolean value, bool isStatic);

    ReflectUtil *field(string name, jbyte value, bool isStatic);

    ReflectUtil *field(string name, jchar value, bool isStatic);

    ReflectUtil *field(string name, jshort value, bool isStatic);

    ReflectUtil *field(string name, jint value, bool isStatic);

    ReflectUtil *field(string name, jlong value, bool isStatic);

    ReflectUtil *field(string name, jfloat value, bool isStatic);

    ReflectUtil *field(string name, jdouble value, bool isStatic);

    ReflectUtil *field(string name, jstring value, bool isStatic);
//    ReflectUtil *field(string name, jobject value, const char *sig, bool isStatic);
//    ReflectUtil * field(string name, jarray value, bool isStatic);
//    ReflectUtil * field(string name, jobjectArray value, bool isStatic);

    ReflectUtil *field(string name, jbooleanArray value, bool isStatic);

    ReflectUtil *field(string name, jbyteArray value, bool isStatic);

    ReflectUtil *field(string name, jcharArray value, bool isStatic);

    ReflectUtil *field(string name, jshortArray value, bool isStatic);

    ReflectUtil *field(string name, jintArray value, bool isStatic);

    ReflectUtil *field(string name, jlongArray value, bool isStatic);

    ReflectUtil *field(string name, jfloatArray value, bool isStatic);

    ReflectUtil *field(string name, jdoubleArray value, bool isStatic);

//    ReflectUtil *field(string name, bool isStatic,...);
    ReflectUtil *method(bool isStatic, string name, string sig, ...);

    ReflectUtil *method(bool isStatic, string name, string sig, RetZFunc retFunc, ...);

    ReflectUtil *method(bool isStatic, string name, string sig, RetBFunc retFunc, ...);

};


#endif //SPACECRAFTANDROID_REFLECTUTIL_H
