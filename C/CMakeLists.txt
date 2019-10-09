cmake_minimum_required(VERSION 3.12)
project(jibuv C)

set(CMAKE_C_FLAGS -IF:/Program Files/Java/jdk1.8.0_202/include)
set(CMAKE_C_STANDARD 99)

if ("${CMAKE_SOURCE_DIR}" STREQUAL "${CMAKE_BINARY_DIR}")
    message(SEND_ERROR "In-source builds are not allowed.")
endif ()

set(PROJECT_CMAKE_IN ${jibuv_SOURCE_DIR}/cmake)
set(PROJECT_DEPS_DIR ${jibuv_SOURCE_DIR}/deps)

set(LIBUV_DEPS_DIR ${PROJECT_DEPS_DIR}/libuv)

configure_file(${PROJECT_CMAKE_IN}/deps.in ${PROJECT_DEPS_DIR}/CMakeLists.txt)
execute_process(COMMAND ${CMAKE_COMMAND} -G "${CMAKE_GENERATOR}" . WORKING_DIRECTORY ${PROJECT_DEPS_DIR})
execute_process(COMMAND ${CMAKE_COMMAND} --build . WORKING_DIRECTORY ${PROJECT_DEPS_DIR})

add_subdirectory(${LIBUV_DEPS_DIR})
include_directories(${LIBUV_DEPS_DIR}/include)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

add_library(jibuv SHARED
        src/loop.c
        src/headers/com_binduv_libuv_handles_Loop.h

        src/uv_handle.c
        src/headers/com_binduv_libuv_handles_UvHandle.h

        src/async.c
        src/headers/com_binduv_libuv_handles_Async.h

        src/tcp.c
        src/headers/com_binduv_libuv_handles_Tcp.h

        src/libuv.c
        src/headers/com_binduv_libuv_handles_Libuv.h)

target_link_libraries(jibuv uv_a)