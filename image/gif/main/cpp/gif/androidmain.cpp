//
// Created by hawks.jamesf on 1/4/20.
//
#include <android_native_app_glue.h>
#include "LogUtil.h"
#define MODULE_NAME "spacecraft_app"
void android_main(struct android_app* state){
    LOGI(MODULE_NAME,"android_main");
}

void android_app_pre_exec_cmd(struct android_app* android_app, int8_t cmd){
    LOGI(MODULE_NAME,"android_app_pre_exec_cmd");

}
void android_app_post_exec_cmd(struct android_app* android_app, int8_t cmd){
    LOGI(MODULE_NAME,"android_app_post_exec_cmd");

}
