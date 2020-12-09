cmake_minimum_required(VERSION 3.4.1)
add_library(
        guard
        SHARED
#        main.cpp
        guard.cpp)


find_library(
        log-lib
        log )

target_link_libraries(
        guard
        ${log-lib} )