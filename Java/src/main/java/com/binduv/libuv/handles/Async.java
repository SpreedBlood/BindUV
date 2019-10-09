package com.binduv.libuv.handles;

import com.binduv.AsyncHandle;
import com.binduv.callbacks.AsyncCallback;
import com.binduv.libuv.handles.enums.UvHandleType;

class Async extends UvHandle implements AsyncHandle {

    private AsyncCallback callback = (asyncHandle) -> {
    };

    Async(long loopPointer) {
        super(loopPointer, UvHandleType.UV_ASYNC);
    }

    public void init() throws Exception {
        int result = uv_async_init(super.getLoopPointer(), super.getHandlePointer());
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    public void send() throws Exception {
        int result = uv_async_send(super.getHandlePointer());
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    /**
     * Do not modify the name of this method, it's being called from the C library.
     */
    private void onCallback() {
        this.callback.onAsync(this);
    }

    public void setCallback(AsyncCallback callback) {
        this.callback = callback;
    }

    /**
     * Initializes an async handle with the given callback that'll be called once the send is happening.
     *
     * @param loopPointer   the pointer to the loop.
     * @param handlePointer the pointer to the async handle.
     * @return 0 if success else x < 0.
     */
    private native int uv_async_init(long loopPointer, long handlePointer);

    /**
     * Will run the handles callback!
     *
     * @param handlePointer the pointer to the async handle.
     * @return 0 if success else x < 0.
     */
    private static native int uv_async_send(long handlePointer);
}
