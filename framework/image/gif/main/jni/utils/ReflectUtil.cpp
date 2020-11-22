//
// Created by hawks.jamesf on 1/11/20.
//

#include "include/ReflectUtil.h"
#include "include/LogUtil.h"

#define MODULE_NAME  "native/utils_reflect"
/**
 * 任何的静态成员都要在这里初始化
 *static start
 */
JNIEnv *ReflectUtil::env = 0;

ReflectUtil ReflectUtil::reflect(jclass type) {
    return ReflectUtil(type);
}

ReflectUtil *ReflectUtil::reflect(jobject object) {
    return new ReflectUtil(env->GetObjectClass(object), object);
}

ReflectUtil ReflectUtil::reflect(string classname) {
    return ReflectUtil(forName(classname));
}

ReflectUtil ReflectUtil::reflect(string classname, jobject classloader) {
    return ReflectUtil(forName(classname, classloader));
}

jclass ReflectUtil::forName(string classname) {
    return env->FindClass(classname.c_str());
}

jclass ReflectUtil::forName(string classname, jobject classloader) {
    return env->DefineClass(classname.c_str(), classloader, nullptr, 0);
}

/**
 * 任何的静态成员都要在这里初始化
 *static end
 */
ReflectUtil ReflectUtil::newInstance(...) {
//    env->CallVoidMethod()
//    env->NewObjectV(type,)
    return ReflectUtil(nullptr);
}

ReflectUtil ReflectUtil::getArgsType(...) {
//    va_list args;
//    va_start(args,count);
//    while(count--)
//    {
//        sum_value+=va_arg(args,int);
//    }
//    env->CallVoidMethod()
//    va_end(args);
    return ReflectUtil(nullptr);
}

ReflectUtil *ReflectUtil::field(string name, bool isStatic = false) {
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jboolean value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), boolType.c_str());
        env->SetBooleanField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), boolType.c_str());
        env->SetStaticBooleanField(type, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jbyte value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), byteType.c_str());
        env->SetByteField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), byteType.c_str());
        env->SetStaticByteField(type, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jchar value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), charType.c_str());
        env->SetCharField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), charType.c_str());
        env->SetStaticCharField(type, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jshort value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), shortType.c_str());
        env->SetShortField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), shortType.c_str());
        env->SetStaticShortField(type, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jint value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), intType.c_str());
        env->SetIntField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), intType.c_str());
        env->SetStaticIntField(type, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jlong value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), longType.c_str());
        env->SetLongField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), longType.c_str());
        env->SetStaticLongField(type, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jfloat value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), floatType.c_str());
        env->SetFloatField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), floatType.c_str());
        env->SetStaticFloatField(type, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jdouble value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), doubleType.c_str());
        env->SetDoubleField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), doubleType.c_str());
        env->SetStaticDoubleField(type, jfieldId, value);
    }
    return this;
}

//ReflectUtil *
//ReflectUtil::field(string name, jobject value, const char *sig, bool isStatic = false) {
//    if (isStatic) {
//        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), sig);
//        env->SetObjectField(object, jfieldId, value);
//    } else {
//        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), sig);
//        env->SetStaticObjectField(type, jfieldId, value);
//    }
//    return this;
//}

ReflectUtil *ReflectUtil::field(string name, jstring value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), stringType.c_str());
        env->SetObjectField(object, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), stringType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    }
    return this;
}

//ReflectUtil *ReflectUtil::field(string name, jarray value, bool isStatic = false) {
//    if (isStatic) {
//        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), arrayty.c_str());
//        env->SetObjectField(object, jfieldId, value);
//    } else {
//        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), boolType.c_str());
//        env->SetStaticBooleanField(type, jfieldId, value);
//    }
//    return this;
//}

//ReflectUtil *ReflectUtil::field(string name, jobjectArray value, bool isStatic = false) {
//    if (isStatic) {
//        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), objectA.c_str());
//        env->SetObjectField(object, jfieldId, value);
//    } else {
//        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), boolType.c_str());
//        env->SetStaticObjectField(type, jfieldId, value);
//    }
//    return this;
//}

ReflectUtil *ReflectUtil::field(string name, jbooleanArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), boolArrayType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), boolArrayType.c_str());
        env->SetObjectField(object, jfieldId, value);

    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jbyteArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), byteArrayType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), byteArrayType.c_str());
        env->SetObjectField(object, jfieldId, value);

    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jcharArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), charArrayType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), charArrayType.c_str());
        env->SetObjectField(object, jfieldId, value);

    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jshortArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), shortArrayType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), shortArrayType.c_str());
        env->SetObjectField(object, jfieldId, value);

    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jintArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), intArrayType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), intArrayType.c_str());
        env->SetObjectField(object, jfieldId, value);

    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jlongArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), longArrayType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), longArrayType.c_str());
        env->SetObjectField(object, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jfloatArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), boolType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), boolType.c_str());
        env->SetObjectField(object, jfieldId, value);
    }
    return this;
}

ReflectUtil *ReflectUtil::field(string name, jdoubleArray value, bool isStatic = false) {
    if (isStatic) {
        jfieldID jfieldId = env->GetStaticFieldID(type, name.c_str(), doubleArrayType.c_str());
        env->SetStaticObjectField(type, jfieldId, value);
    } else {
        jfieldID jfieldId = env->GetFieldID(type, name.c_str(), doubleArrayType.c_str());
        env->SetObjectField(object, jfieldId, value);
    }
    return this;
}

ReflectUtil *
ReflectUtil::method(bool isStatic, string name, string sig, RetZFunc retFunc, ...) {
    va_list args;
    va_start(args, isStatic);
    if (isStatic) {
        jmethodID methodID = env->GetStaticMethodID(type, name.c_str(), sig.c_str());
        retFunc(env->CallStaticBooleanMethodV(type, methodID, args));
    } else {
        jmethodID methodID = env->GetMethodID(type, name.c_str(), sig.c_str());
        retFunc(env->CallBooleanMethodV(object, methodID, args));

    }
//    env->CallNonvirtualBooleanMethodV(object,type,methodID,args);
    va_end(args);
    return nullptr;
}

ReflectUtil *
ReflectUtil::method(bool isStatic, string name, string sig, RetBFunc retFunc, ...) {
    va_list args;
    va_start(args, isStatic);
    if (isStatic) {
        jmethodID methodID = env->GetStaticMethodID(type, name.c_str(), sig.c_str());
        retFunc(env->CallStaticByteMethodV(type, methodID, args));
    } else {
        jmethodID methodID = env->GetMethodID(type, name.c_str(), sig.c_str());
        retFunc(env->CallByteMethodV(object, methodID, args));
    }
//    env->CallNonvirtualBooleanMethodV(object,type,methodID,args);
    va_end(args);
    return nullptr;
}

ReflectUtil *
ReflectUtil::method(bool isStatic, string name, string sig, ...) {
    LOGI(MODULE_NAME, "object: %d", object);
    va_list args;
    va_start(args, isStatic);
    if (isStatic) {
        jmethodID methodID = env->GetStaticMethodID(type, name.c_str(), sig.c_str());
        env->CallStaticVoidMethodV(type, methodID, args);
    } else {
        jmethodID methodID = env->GetMethodID(type, name.c_str(), sig.c_str());
        env->CallVoidMethodV(object, methodID, args);
    }
//    env->CallNonvirtualBooleanMethodV(object,type,methodID,args);
    va_end(args);
    return nullptr;
}



