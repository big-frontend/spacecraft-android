#==============================breakpad module======================================
aux_source_directory(${COMMON_PROJECT}/breakpad SOURCE_DIR_BREAKPAD)
aux_source_directory(${COMMON_PROJECT}/fb SOURCE_DIR_FB)
#include_directories(${COMMON_PROJECT}/fb)
add_library(
        breakpad
        STATIC
        ${SOURCE_DIR_FB}
        ${SOURCE_DIR_BREAKPAD})
target_link_libraries(breakpad log)