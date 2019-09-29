cmake_minimum_required(VERSION 3.12)
project(jibuv C)

set(CMAKE_C_FLAGS -IF:/Program Files/Java/jdk1.8.0_202/include)
set(CMAKE_C_STANDARD 99)

if ("${CMAKE_SOURCE_DIR}" STREQUAL "${CMAKE_BINARY_DIR}")
    message(SEND_ERROR "In-source builds are not allowed.")
endif ()

include(vendor/uv.cmake)
set(LIBUV_LIBRARIES uv)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

add_library(jibuv SHARED
        src/loop.c
        src/headers/com_binduv_libuv_handles_LoopHandle.h

        src/uv_handle.c
        src/headers/com_binduv_libuv_handles_UvHandle.h

        src/async.c
        src/headers/com_binduv_libuv_handles_AsyncHandle.h

        src/tcp.c

        src/headers/com_binduv_libuv_Libuv.h src/libuv.c
        src/headers/com_binduv_libuv_handles_TcpHandle.h)

target_link_libraries(jibuv ${LIBUV_LIBRARIES})