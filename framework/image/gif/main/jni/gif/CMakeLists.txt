cmake_minimum_required(VERSION 3.4.1)
add_library(gif_jni SHARED
        jni_load.cpp com_hawksjamesf_image_GifPlayer.cpp androidmain.cpp
        GifPlayer.cpp

        )
find_library( # Sets the name of the path variable.
        log-lib
        log)

target_include_directories(gif_jni PRIVATE

        ${ANDROID_NDK}/sources/android/native_app_glue
        ../codec/include
        ../utils/include
        )

target_link_libraries( # Specifies the target library.
        gif_jni

        gifcodec android z EGL jnigraphics mediandk
        OpenMAXAL
        #        vulkan
        #        sync
        #        camera2ndk
        #        nativewindow
        z
        m
        ${log-lib}
        utils
        )