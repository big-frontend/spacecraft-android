cmake_minimum_required(VERSION 3.4.1)
set(CMAKE_VERBOSE_MAKEFILE on)

#==============gif codec
set(gifcodec_src_dir src/main/jni/gif_codec)
set(gifcodec_src_files
        ${gifcodec_src_dir}/dgif_lib.c ${gifcodec_src_dir}/egif_lib.c ${gifcodec_src_dir}/gif_err.c ${gifcodec_src_dir}/gif_font.c
        ${gifcodec_src_dir}/gif_hash.c ${gifcodec_src_dir}/gifalloc.c ${gifcodec_src_dir}/openbsd-reallocarray.c ${gifcodec_src_dir}/quantize.c)
add_library(gifcodec STATIC ${gifcodec_src_files})
target_link_libraries(gifcodec log)

#==============gif player
set(gifplayer_src_dir src/main/jni/gif_player)
set(gifplayer_src_files
        ${gifplayer_src_dir}/jni_load.cpp ${gifplayer_src_dir}/androidmain.cpp
        ${gifplayer_src_dir}/GifPlayer.cpp
        ${gifplayer_src_dir}/gif_jni.cpp
        )
add_library(gifplayer SHARED ${gifcodec_src_files})
#target_include_directories(framesequence PRIVATE
#        ${ANDROID_NDK}/sources/android/native_app_glue ${gifcodec_src_dir}/include
#        )
target_link_libraries( # Specifies the target library.
        gifplayer
        gifcodec android z EGL jnigraphics mediandk
        OpenMAXAL
        #        vulkan
        #        sync
        #        camera2ndk
        #        nativewindow
        z m log
        )