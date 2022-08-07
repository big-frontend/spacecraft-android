cmake_minimum_required(VERSION 3.4.1)
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}   -Wno-format -Wno-sign-compare -Wno-unused-parameter -DHAVE_CONFIG_H")
add_definitions(-DENABLE_ANDROID_LOG)
set(PACKAGE_NAME "jsruntime")

######################## jsc & jsc-android so
#if (${ANDROID_ABI} STREQUAL "armeabi-v7a")
#
#endif ()
add_library(jsc-android SHARED src/main/cpp/jsc-jni.cpp)
target_include_directories(jsc-android PUBLIC ${CMAKE_SOURCE_DIR}/src/main/cpp/jsc/include)
#  问题：2 files found with path 'lib/armeabi-v7a/libicu_common.so' from inputs
# root cause：gradle merged_jni_libs任务会import jniLibs  与 cmake 的add_library(icu_commmon SHARED IMPORTED) import冲突

#导入jnilibs中的第三方动态库
add_library(jsc SHARED IMPORTED)
set_target_properties(jsc PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libjsc.so)
add_library(icu_commmon SHARED IMPORTED)
set_target_properties(icu_commmon PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libicu_common.so)
find_package(fbjni REQUIRED CONFIG)
#将jsc header文件 链接到 a动态库
target_link_libraries(jsc-android log z
        jsc icu_commmon
        fbjni::fbjni
        )

######################## hermes & hermes-android so
#add_library(hermes SHARED IMPORTED)
#set_target_properties(hermes PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libhermes.so)
## Third-party prefabs
##find_package(hermes-engine REQUIRED CONFIG)
#target_link_libraries(hermes-anadroid log z
#        #        hermes-engine::libhermes
#        hermes
#        )
######################## quickjs & quickjs-android so
#include_directories(src/main/cpp/quickjs)
set(SOURCE_DIR
        src/main/cpp/quickjs/cutils.c
        src/main/cpp/quickjs/libbf.c
        src/main/cpp/quickjs/libregexp.c
        src/main/cpp/quickjs/libunicode.c
        src/main/cpp/quickjs/quickjs.c
        )

file(STRINGS "src/main/cpp/quickjs/VERSION" CONFIG_VERSION)
add_definitions(-DCONFIG_VERSION="${CONFIG_VERSION}")
add_definitions(-DCONFIG_BIGNUM)
add_definitions(-D_GNU_SOURCE)
add_definitions(-DCONFIG_CC="gcc")
add_definitions(-DCONFIG_PREFIX="/usr/local")
add_library(
        quickjs
        SHARED
        ${SOURCE_DIR}
)
target_include_directories(quickjs PUBLIC ${CMAKE_SOURCE_DIR}/src/main/cpp)

add_library(quickjs-android SHARED src/main/cpp/quickjs-jni.cpp)
target_include_directories(quickjs-android PRIVATE ${CMAKE_SOURCE_DIR}/src/main/cpp)
target_link_libraries(quickjs-android log quickjs)