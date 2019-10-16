#include "headers/com_binduv_libuv_handles_Async.h"
#include <uv.h>
#include <assert.h>

static void async_callback(uv_async_t *handle);

struct async_class_s {
    JNIEnv *env;
    jclass asyncHandle;
} typedef async_class_t;

static boolean callbackInitialized = FALSE;
static jmethodID onAsync;

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Async_uv_1async_1init(JNIEnv *env, jobject clazz, jlong loop_pointer,
                                                    jlong handle_pointer) {
    assert(loop_pointer);
    assert(handle_pointer);

    uv_async_t *async_handle;
    async_class_t *async_class;

    async_class = malloc(sizeof(async_class_t *));
    jclass cls = (*env)->NewGlobalRef(env, clazz);

    if (!callbackInitialized) {
        onAsync = (*env)->GetMethodID(env, (*env)->GetObjectClass(env, clazz), "onCallback", "()V");
        callbackInitialized = TRUE;
    }

    async_class->env = env;
    async_class->asyncHandle = cls;

    async_handle = (uv_async_t *) handle_pointer;

    async_handle->data = async_class;

    return uv_async_init((uv_loop_t *) loop_pointer, async_handle, async_callback);
}

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Async_uv_1async_1send(JNIEnv *env, jobject clazz, jlong handle_pointer) {
    assert(handle_pointer);

    uv_async_t *async_handle;

    async_handle = (uv_async_t *) handle_pointer;

    return uv_async_send(async_handle);
}

static void async_callback(uv_async_t *handle) {
    async_class_t *async_class;
    JNIEnv *env;

    async_class = (async_class_t *) handle->data;
    env = async_class->env;
    (*env)->CallVoidMethod(env, async_class->asyncHandle, onAsync);
}