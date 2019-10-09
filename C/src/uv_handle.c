#include "headers/com_binduv_libuv_handles_UvHandle.h"
#include <uv.h>

JNIEXPORT jlong JNICALL
Java_com_binduv_libuv_handles_UvHandle_uv_1handle_1init(JNIEnv *env, jclass jclass, jint handleType) {
    size_t handle_size = uv_handle_size(handleType);
    size_t *handle = malloc(handle_size);

    return (jlong) handle;
}

JNIEXPORT void
JNICALL Java_com_binduv_libuv_handles_UvHandle_uv_1close(JNIEnv *env, jclass class, jlong handle_pointer) {
    uv_close((uv_handle_t *) handle_pointer, NULL);
}