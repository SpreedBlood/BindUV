package com.binduv.libuv.handles;

import com.binduv.libuv.handles.enums.UvHandleType;

abstract class UvHandle {

    private long loopPointer;
    private long handlePointer;

    UvHandle(long loopPointer, UvHandleType uvHandleType) {
        this.loopPointer = loopPointer;
        this.handlePointer = uv_handle_init(uvHandleType.getValue());
    }

    long getHandlePointer() {
        return this.handlePointer;
    }

    long getLoopPointer() {
        return this.loopPointer;
    }

    /**
     * Initializes the given handle.
     *
     * @param handle_type the handle type to initialize.
     * @return the pointer to the handle.
     */
    static native long uv_handle_init(int handle_type);

}
