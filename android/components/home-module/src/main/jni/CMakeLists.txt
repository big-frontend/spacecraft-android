cmake_minimum_required(VERSION 3.4.1)
add_library(client_key SHARED
        client_key.cpp
        )
find_library( # Sets the name of the path variable.
        log-lib
        log)
#target_include_directories(client_key PRIVATE
#
#        ${ANDROID_NDK}/sources/android/native_app_glue
#        ../codec/include
#        ../utils/include
#        )

target_link_libraries( # Specifies the target library.
        client_key
        ${log-lib}
        )