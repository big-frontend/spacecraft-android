//
// Created by guard.jamesf on 12/19/19.
//

#ifndef SPACECRAFTANDROID_GUARD_H
#define SPACECRAFTANDROID_GUARD_H

#include <android/log.h>
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

class guard {

};



#endif //SPACECRAFTANDROID_GUARD_H
