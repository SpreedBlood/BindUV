/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_binduv_libuv_handles_LoopHandle */

#ifndef _Included_com_binduv_libuv_handles_LoopHandle
#define _Included_com_binduv_libuv_handles_LoopHandle
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_binduv_libuv_handles_LoopHandle
 * Method:    new_loop_handle
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_binduv_libuv_handles_LoopHandle_new_1loop_1handle
  (JNIEnv *, jclass);

/*
 * Class:     com_binduv_libuv_handles_LoopHandle
 * Method:    uv_run
 * Signature: (JI)V
 */
JNIEXPORT jint JNICALL Java_com_binduv_libuv_handles_LoopHandle_uv_1run
  (JNIEnv *, jclass, jlong, jint);

#ifdef __cplusplus
}
#endif
#endif
