#include <jni.h>
#include <string.h>
#include <inttypes.h>
#include <pthread.h>
#include <android/log.h>
#include <assert.h>
#include <time.h>
#include <stdbool.h>


/**
 *
 * 目前通用的以64位系统为主。
 *
 * 数据类型
 * c++           |  c           | 内存分配
 * bool          |              |
 *           |              |
 *  char         | char         | 1byte
 * unsigned char |  unsigned char| 1byte
 * signed char   | signed char  | 1byte
 * int/signed int | int          | 2bytes or 4bytes
 * unsigned int  | unsigned int | 2bytes or 4bytes
 * short int/signed short int | short        | 2bytes
 * unsigned short int | unsigned short | 2bytes
 *
 *
 *               | long          |4bytes
 *               | unsigned long |4bytes
 * long int      |               | 8bytes
 * signed long int|               | 8bytes
 * unsigned long int|               | 8bytes
 *
 * float         | float         | 4bytes
 * double        | double        | 8bytes
 * long double   | long double   | 16bytes
 * wchar_t       |               | 2 或 4 个字节
 * void* 为对象地址
 */
//
// Created by hawks.jamesf on 12/29/19.
//
//JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
//    JNIEnv *env;
//    (*vm)->GetEnv(NULL, NULL, NULL);
//    return JNI_VERSION_1_6;
//}

