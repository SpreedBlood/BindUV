package com.binduv.libuv.handles;

import com.binduv.libuv.handles.enums.UvHandleType;

public class AsyncHandle extends UvHandle {

    private long loopPointer;

    AsyncHandle(long loopPointer) {
        super(loopPointer, UvHandleType.UV_ASYNC);

        this.loopPointer = loopPointer;
    }

    public void initAsyncHandle(Runnable callback) {
        int result = uv_async_init(this.loopPointer, super.getHandlePointer(), callback);
    }

    public void send() {
        int result = uv_async_send(super.getHandlePointer());
    }

    /**
     * Initializes an async handle with the given callback that'll be called once the send is happening.
     *
     * @param loopPointer   the pointer to the loop.
     * @param handlePointer the pointer to the async handle.
     * @param callback      the callback.
     * @return 0 if success else x < 0.
     */
    private static native int uv_async_init(long loopPointer, long handlePointer, Runnable callback);

    /**
     * Will run the handles callback!
     *
     * @param handlePointer the pointer to the async handle.
     * @return 0 if success else x < 0.
     */
    private static native int uv_async_send(long handlePointer);

}
