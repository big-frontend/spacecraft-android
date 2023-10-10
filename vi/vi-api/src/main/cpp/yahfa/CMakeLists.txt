file(GLOB_RECURSE source_files "${CMAKE_CURRENT_SOURCE_DIR}/*.c")
add_library(yahfa SHARED ${source_files})
# class visibly init only needed after Android R for arm
if (${ANDROID_ABI} MATCHES "arm")
    find_package(dlfunc REQUIRED CONFIG)
    target_link_libraries(yahfa log dlfunc::dlfunc)
else ()
    target_link_libraries(yahfa log)
endif ()

