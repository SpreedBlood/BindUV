#include "headers/com_binduv_libuv_handles_Libuv.h"
#include <uv.h>

JNIEXPORT jstring JNICALL Java_com_binduv_libuv_Libuv_uv_1err_1name
        (JNIEnv *env, jclass class, jint status) {
    return (*env)->NewStringUTF(env, uv_err_name(status));
}