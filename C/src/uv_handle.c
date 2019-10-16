#include "headers/com_binduv_libuv_handles_UvHandle.h"
#include "headers/uv_handle.h"
#include <uv.h>
#include <assert.h>

static boolean callbackInitialized = FALSE;
static jmethodID onClose;

static void on_close(uv_handle_t *peer);

JNIEXPORT jlong JNICALL
Java_com_binduv_libuv_handles_UvHandle_uv_1handle_1init(JNIEnv *env, jclass jclass, jint handleType) {
    size_t handle_size = uv_handle_size(handleType);
    size_t *handle = malloc(handle_size);

    if (!callbackInitialized) {
        onClose = (*env)->GetMethodID(env, jclass, "onClose", "()V");
        callbackInitialized = TRUE;
    }

    return (jlong) handle;
}

JNIEXPORT void
JNICALL Java_com_binduv_libuv_handles_UvHandle_uv_1close(JNIEnv *env, jclass class, jlong handle_pointer) {
    assert(handle_pointer);

    uv_close((uv_handle_t *) handle_pointer, on_close);
}

static void on_close(uv_handle_t *peer) {
    handle_class_t *handle_class;
    JNIEnv *env;

    handle_class = peer->data;
    env = handle_class->env;
    (*env)->CallVoidMethod(env, handle_class->handle, onClose);
    (*env)->DeleteGlobalRef(env, handle_class->handle);
    free(peer);
}