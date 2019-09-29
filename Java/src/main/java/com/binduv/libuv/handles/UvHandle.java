package com.binduv.libuv.handles;

import com.binduv.libuv.handles.enums.UvHandleType;

abstract class UvHandle {

    private long handlePointer;

    UvHandle(long loopPointer, UvHandleType uvHandleType) {
        this.handlePointer = uv_handle_init(uvHandleType.getValue());
    }

    protected long getHandlePointer() {
        return this.handlePointer;
    }

    /**
     * Initializes the given handle.
     *
     * @param handle_type the handle type to initialize.
     * @return the pointer to the handle.
     */
    public static native long uv_handle_init(int handle_type);

}
