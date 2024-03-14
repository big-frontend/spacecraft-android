cmake_minimum_required(VERSION 3.10.2)
set(CMAKE_VERBOSE_MAKEFILE on)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}   -Wno-format -Wno-sign-compare -Wno-unused-parameter -DHAVE_CONFIG_H")
add_definitions(-DENABLE_ANDROID_LOG)

add_subdirectory(src/main/cpp/clientkey)
add_subdirectory(src/main/cpp/y)
