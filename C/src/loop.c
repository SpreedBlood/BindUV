#include "headers/com_binduv_libuv_handles_LoopHandle.h"
#include <uv.h>
#include <assert.h>

JNIEXPORT jlong JNICALL Java_com_binduv_libuv_handles_LoopHandle_new_1loop_1handle(JNIEnv *env, jclass jclass) {
    uv_loop_t *loop;
    loop = uv_loop_new();

    return (jlong) loop;
}

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_LoopHandle_uv_1run(JNIEnv *env, jclass jclass, jlong loop_pointer, jint run_mode) {
    assert(loop_pointer);
    uv_loop_t *loop;
    loop = (uv_loop_t *) loop_pointer;

    uv_run(loop, run_mode);
}