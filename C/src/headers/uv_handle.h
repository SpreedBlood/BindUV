#ifndef JIBUV_UV_HANDLE_H
#define JIBUV_UV_HANDLE_H

#include <jni.h>

struct handle_class_s {
    JNIEnv *env;
    jclass handle;
} typedef handle_class_t;

#endif //JIBUV_UV_HANDLE_H
