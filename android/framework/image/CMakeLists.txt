cmake_minimum_required(VERSION 3.4.1)
set(CMAKE_VERBOSE_MAKEFILE on)

#==============gif codec
set(gifcodec_src_dir src/main/jni/gif_codec)
set(gifcodec_src_files
        ${gifcodec_src_dir}/dgif_lib.c ${gifcodec_src_dir}/egif_lib.c ${gifcodec_src_dir}/gif_err.c ${gifcodec_src_dir}/gif_font.c
        ${gifcodec_src_dir}/gif_hash.c ${gifcodec_src_dir}/gifalloc.c ${gifcodec_src_dir}/openbsd-reallocarray.c ${gifcodec_src_dir}/quantize.c)
add_library(gifcodec STATIC ${gifcodec_src_files})
target_link_libraries(gifcodec log)

#==============framesequence
set(frameseq_src_dir src/main/jni/frame_sequence)
set(frameseq_src_files
        ${frameseq_src_dir}/BitmapDecoderJNI.cpp
        ${frameseq_src_dir}/FrameSequence.cpp
        ${frameseq_src_dir}/FrameSequenceJNI.cpp
        ${frameseq_src_dir}/FrameSequence_gif.cpp
        ${frameseq_src_dir}/JNIHelpers.cpp
        ${frameseq_src_dir}/Registry.cpp
        ${frameseq_src_dir}/Stream.cpp
        )
add_library(framesequence SHARED ${frameseq_src_files})
#ifeq ($(FRAMESEQUENCE_INCLUDE_WEBP),true)
#	LOCAL_C_INCLUDES += external/webp/include
#	LOCAL_SRC_FILES += FrameSequence_webp.cpp
#	LOCAL_STATIC_LIBRARIES += libwebp-decode
#endif
target_include_directories(framesequence PRIVATE
        ${ANDROID_NDK}/sources/android/native_app_glue ${gifcodec_src_dir}/include
        )

target_link_libraries( # Specifies the target library.
        framesequence

        gifcodec android z EGL jnigraphics mediandk
        OpenMAXAL
        #        vulkan
        #        sync
        #        camera2ndk
        #        nativewindow
        z m log
        )

#==============gif player
set(gifplayer_src_dir src/main/jni/gif_player)
set(gifplayer_src_files
        ${gifplayer_src_dir}/jni_load.cpp ${gifplayer_src_dir}/androidmain.cpp
        ${gifplayer_src_dir}/GifPlayer.cpp
        ${gifplayer_src_dir}/gif_jni.cpp
        )
add_library(gifplayer SHARED ${gifcodec_src_files})
target_include_directories(framesequence PRIVATE
        ${ANDROID_NDK}/sources/android/native_app_glue ${gifcodec_src_dir}/include
        )
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