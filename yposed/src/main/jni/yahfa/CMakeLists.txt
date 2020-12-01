cmake_minimum_required(VERSION 3.4.1)
add_library(
        yahfa
        SHARED
        HookMain.c trampoline.c dl.c)
find_library(
        log-lib
        log )

#target_include_directories(hotfix PUBLIC ../guard/include HotFix.h)
target_link_libraries(
        yahfa
        ${log-lib} )