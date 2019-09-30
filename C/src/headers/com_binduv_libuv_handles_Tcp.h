/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_binduv_libuv_handles_Tcp */

#ifndef _Included_com_binduv_libuv_handles_Tcp
#define _Included_com_binduv_libuv_handles_Tcp
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_binduv_libuv_handles_Tcp
 * Method:    uv_tcp_init
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_Tcp_uv_1tcp_1init
        (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     com_binduv_libuv_handles_Tcp
 * Method:    uv_tcp_bind
 * Signature: (JLjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_Tcp_uv_1tcp_1bind
        (JNIEnv *, jclass, jlong, jstring, jint);

/*
 * Class:     com_binduv_libuv_handles_Tcp
 * Method:    uv_listen
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_Tcp_uv_1listen
        (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     com_binduv_libuv_handles_Tcp
 * Method:    uv_accept
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_Tcp_uv_1accept
        (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     com_binduv_libuv_handles_Tcp
 * Method:    uv_read_start
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_Tcp_uv_1read_1start
        (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
