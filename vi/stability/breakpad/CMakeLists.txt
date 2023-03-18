cmake_minimum_required(VERSION 3.10.2)
set(CMAKE_VERBOSE_MAKEFILE on)
project(common VERSION 1.0.0 LANGUAGES CXX C)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}   -Wno-format -Wno-sign-compare -Wno-unused-parameter -DHAVE_CONFIG_H")
add_definitions(-DENABLE_ANDROID_LOG)

#add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/src/main/cpp/breakpad)
