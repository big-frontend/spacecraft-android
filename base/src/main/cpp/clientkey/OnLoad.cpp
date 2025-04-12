#include <jni.h>
#include <ctime>
#include <android/log.h>
//#include <log.h>
#include <android/bitmap.h>
#include <sys/stat.h>
#include <cstdio>

bool getState(struct stat st){
    int result = stat("/data/data", &st);
    if (result == 0) {
// 成功获取信息
        mode_t permissions = st.st_mode & 0777;  // 权限位
        off_t size = st.st_size;                 // 大小（字节）
        time_t mtime = st.st_mtime;              // 最后修改时间
    } else {
        perror("stat failed");
    }
}


extern "C"
JNIEXPORT jstring
JNICALL
Java_com_electrolytej_network_Crypto_getClientKey(JNIEnv *env, jclass thiz,
                                                       jstring jstr_token,
                                                       jlong cur_time) {
//    int *p = nullptr;
//    int a = 1 + (*p);
//    __android_log_print(ANDROID_LOG_VERBOSE, "electrolytej", "The value of 1 + 1 is %d", a);
    struct stat st;
    return nullptr;
}