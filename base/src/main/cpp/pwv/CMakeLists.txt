file(GLOB_RECURSE source_files "${CMAKE_CURRENT_SOURCE_DIR}/*.cc")
add_library(pwv SHARED ${source_files})
#GLESv3.1
target_link_libraries(pwv log)