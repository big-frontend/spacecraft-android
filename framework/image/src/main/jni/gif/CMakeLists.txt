cmake_minimum_required(VERSION 3.4.1)

#==============gif util
set(utils_src_dir utils)
add_library( # Sets the name of the library.
        utils STATIC ${utils_src_dir}/AssetUtil.cpp ${utils_src_dir}/ThreadUtil.cpp ${utils_src_dir}/ReflectUtil.cpp)
target_link_libraries( # Specifies the target library.
        utils
        android z EGL jnigraphics mediandk log)
#==============gif codec
set(gifcodec_src_file
        codec/dgif_lib.c codec/egif_lib.c codec/gif_err.c codec/gif_font.c
        codec/gif_hash.c codec/gifalloc.c codec/openbsd-reallocarray.c codec/quantize.c)
add_library(gifcodec STATIC ${gifcodec_src_file})
target_link_libraries(gifcodec log)

#==============gif jni

add_library(framesequence SHARED
#        jni_load.cpp androidmain.cpp
        GifPlayer.cpp
        gif_jni.cpp
        BitmapDecoderJNI.cpp
        FrameSequence.cpp
        FrameSequenceJNI.cpp
        FrameSequence_gif.cpp
        JNIHelpers.cpp
        Registry.cpp
        Stream.cpp
        )
#ifeq ($(FRAMESEQUENCE_INCLUDE_WEBP),true)
#	LOCAL_C_INCLUDES += external/webp/include
#	LOCAL_SRC_FILES += FrameSequence_webp.cpp
#	LOCAL_STATIC_LIBRARIES += libwebp-decode
#endif
target_include_directories(framesequence PRIVATE
        ${ANDROID_NDK}/sources/android/native_app_glue codec/include utils/include utils
        )

target_link_libraries( # Specifies the target library.
        framesequence

        gifcodec android z EGL jnigraphics mediandk
        OpenMAXAL
        #        vulkan
        #        sync
        #        camera2ndk
        #        nativewindow
        z m log utils
        )