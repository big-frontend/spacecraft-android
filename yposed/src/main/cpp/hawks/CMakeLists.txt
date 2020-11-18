cmake_minimum_required(VERSION 3.4.1)
add_library(
        hawks
        SHARED
        hawks.cpp)


find_library(
        log-lib
        log )

target_link_libraries(
        hawks
        ${log-lib} )