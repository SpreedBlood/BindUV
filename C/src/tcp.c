#include "headers/com_binduv_libuv_handles_Tcp.h"
#include "headers/uv_handle.h"
#include <uv.h>
#include <assert.h>

static void on_connection(uv_stream_t *server, int status);

static void buf_alloc(uv_handle_t *handle,
                      size_t suggested_size,
                      uv_buf_t *buf);

static void after_read(uv_stream_t *handle,
                       ssize_t nread,
                       const uv_buf_t *buf);

static void on_close(uv_handle_t* peer);

JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_Tcp_uv_1tcp_1init
        (JNIEnv *env, jobject class, jlong loop_pointer, jlong tcp_pointer) {
    assert(loop_pointer);
    assert(tcp_pointer);
    uv_tcp_t *tcp;
    handle_class_t *handle_class;

    tcp = (uv_tcp_t *) tcp_pointer;

    jobject instance = (*env)->NewGlobalRef(env, class);
    handle_class = malloc(sizeof(handle_class_t *));

    handle_class->env = env;
    handle_class->handle= instance;

    tcp->data = handle_class;

    return uv_tcp_init((uv_loop_t *)loop_pointer, tcp);
}

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Tcp_uv_1tcp_1bind(JNIEnv *env, jclass clazz, jlong tcp_pointer, jstring host,
                                                      jint port) {
    assert(tcp_pointer);
    uv_tcp_t *tcp;
    struct sockaddr_in addr;

    tcp = (uv_tcp_t *) tcp_pointer;
    uv_ip4_addr((const char *) host, port, &addr);

    return uv_tcp_bind(tcp, (const struct sockaddr *) &addr, 0);
}

static boolean callbackInitialized = FALSE;
static jmethodID onConnect;
static jmethodID onDisconnect;
static jmethodID onRead;

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Tcp_uv_1listen(JNIEnv *env, jclass clazz, jlong tcp_pointer, jint backlog) {
    assert(tcp_pointer);

    if (!callbackInitialized) {
        onConnect = (*env)->GetMethodID(env, clazz, "onConnect", "()V");
        onDisconnect = (*env)->GetMethodID(env, clazz, "onDisconnect", "()V");
        onRead = (*env)->GetMethodID(env, clazz, "onRead", "([BJ)V");
        callbackInitialized = TRUE;
    }

    return uv_listen((uv_stream_t *) tcp_pointer, backlog, on_connection);
}

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Tcp_uv_1accept(JNIEnv *env, jclass class, jlong server_pointer,
                                                   jlong client_pointer) {
    assert(server_pointer);
    assert(client_pointer);

    return uv_accept((uv_stream_t *) server_pointer, (uv_stream_t *) client_pointer);
}

JNIEXPORT jint JNICALL
Java_com_binduv_libuv_handles_Tcp_uv_1read_1start(JNIEnv *env, jclass class, jlong client_pointer) {
    assert(client_pointer);

    return uv_read_start((uv_stream_t *) client_pointer, buf_alloc, after_read);
}

static void on_connection(uv_stream_t *server, int status) {
    handle_class_t *handle_class;
    JNIEnv *env;

    handle_class = server->data;
    env = handle_class->env;

    (*env)->CallVoidMethod(env, handle_class->handle, onConnect);
}

static void buf_alloc(uv_handle_t *handle,
                      size_t suggested_size,
                      uv_buf_t *buf) {
    buf->base = malloc(suggested_size);
    buf->len = suggested_size;
}

static void after_read(uv_stream_t *handle,
                       ssize_t nread,
                       const uv_buf_t *buf) {
    handle_class_t *handle_class;
    JNIEnv *env;

    handle_class = handle->data;
    env = handle_class->env;
    if (nread == UV_EOF) {
        (*env)->CallVoidMethod(env, handle_class->handle, onDisconnect);

        (*env)->DeleteGlobalRef(env, handle_class->handle);
        uv_close((uv_handle_t*) handle, on_close);
        free(buf->base);
        return;
    }

    jbyteArray arr = (*env)->NewByteArray(env, nread);
    (*env)->SetByteArrayRegion(env, arr, 0, nread, (jbyte *) buf->base);

    (*env)->CallVoidMethod(env, handle_class->handle, onRead, arr, nread);

    //free(buf->base);
}

static void on_close(uv_handle_t* peer) {
    free(peer);
}