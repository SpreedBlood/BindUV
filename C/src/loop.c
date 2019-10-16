#include "headers/com_binduv_libuv_handles_Loop.h"
#include "headers/uv_handle.h"
#include <uv.h>
#include <assert.h>

static void on_walk(uv_handle_t *peer, void *arg);

static void on_close(uv_handle_t *peer);

JNIEXPORT jlong JNICALL Java_com_binduv_libuv_handles_Loop_new_1loop_1handle(JNIEnv *env, jclass jclass) {
    uv_loop_t *loop;
    loop = uv_loop_new();

    return (jlong) loop;
}

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Loop_uv_1run(JNIEnv *env, jclass jclass, jlong loop_pointer, jint run_mode) {
    assert(loop_pointer);
    uv_loop_t *loop;
    loop = (uv_loop_t *) loop_pointer;

    uv_run(loop, run_mode);
}

JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_Loop_uv_1loop_1close
        (JNIEnv *env, jclass class, jlong loop_pointer) {
    assert(loop_pointer);

    uv_loop_t *loop;
    loop = (uv_loop_t *) loop_pointer;
    uv_loop_close(loop);
    uv_walk(loop, on_walk, NULL);
    int r = uv_run(loop, UV_RUN_DEFAULT);
    return r;
}

static void on_walk(uv_handle_t *peer, void *arg) {
    if (!uv_is_closing(peer)) {
        uv_close(peer, on_close);
    }
}

static void on_close(uv_handle_t *peer) {
    handle_class_t *handle_class;
    JNIEnv *env;

    handle_class = peer->data;
    env = handle_class->env;
    (*env)->DeleteGlobalRef(env, handle_class->handle);
    free(peer);
}