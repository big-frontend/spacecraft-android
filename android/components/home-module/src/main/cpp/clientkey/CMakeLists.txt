file(GLOB_RECURSE source_files "${CMAKE_CURRENT_SOURCE_DIR}/*.cpp")
add_library(client_key SHARED ${source_files})
target_link_libraries(client_key log)