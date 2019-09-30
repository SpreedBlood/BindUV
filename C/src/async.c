#include "headers/com_binduv_libuv_handles_Async.h"
#include <uv.h>
#include <assert.h>

static void async_callback(uv_async_t *handle);

struct runnable_interface_s {
    JNIEnv *env;
    jclass runnable;
    jmethodID mid;
} typedef runnable_interface_t;

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Async_uv_1async_1init(JNIEnv *env, jobject clazz, jlong loop_pointer,
                                                          jlong handle_pointer, jobject runnable) {
    assert(loop_pointer);

    runnable_interface_t *runnable_int = malloc(sizeof(runnable_interface_t *));

    jclass cls = (*env)->GetObjectClass(env, runnable);
    jmethodID mid = (*env)->GetMethodID(env, cls, "run", "()V");

    runnable_int->env = env;
    runnable_int->runnable = runnable;
    runnable_int->mid = mid;

    uv_async_t *async_handle;
    uv_loop_t *loop;

    async_handle = (uv_async_t *) handle_pointer;

    loop = (uv_loop_t *) loop_pointer;

    async_handle->data = runnable_int;

    return uv_async_init(loop, async_handle, async_callback);
}

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Async_uv_1async_1send(JNIEnv *env, jobject clazz, jlong handle_pointer) {
    assert(handle_pointer);

    uv_async_t *async_handle;

    async_handle = (uv_async_t *) handle_pointer;

    return uv_async_send(async_handle);
}

static void async_callback(uv_async_t *handle) {
    runnable_interface_t *runnable_interface;

    JNIEnv *env;
    jclass runnable;
    jmethodID mid;

    runnable_interface = (runnable_interface_t *) handle->data;
    env = runnable_interface->env;
    runnable = runnable_interface->runnable;
    mid = runnable_interface->mid;

    (*env)->CallVoidMethod(env, runnable, mid);
}