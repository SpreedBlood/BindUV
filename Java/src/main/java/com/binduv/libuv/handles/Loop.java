package com.binduv.libuv.handles;

import com.binduv.LoopHandle;
import com.binduv.libuv.handles.enums.UvRunMode;

import java.io.IOException;

class Loop implements LoopHandle {

    private long pointer;

    static {
        System.loadLibrary("jibuv");
    }

    Loop() {
        this.pointer = new_loop_handle();
    }

    @Override
    public long getPointer() {
        return this.pointer;
    }

    @Override
    public void run(UvRunMode runMode) throws Exception {
        int result = uv_run(this.pointer, runMode.getRunMode());
        if (result < 0) {
             throw new Exception(Libuv.uv_err_name(result));
        }
    }

    @Override
    public void close() throws IOException {
        int result = uv_loop_close(this.pointer);
        if (result < 0) {
            throw new IOException(Libuv.uv_err_name(result));
        }
    }

    /**
     * This will create a new loop handle
     *
     * @return the pointer to the loop.
     */
    private static native long new_loop_handle();

    /**
     * Will start running the given loop pointer.
     *
     * @param pointer  the pointer to the loop.
     * @param run_mode the mode to run.
     */
    private static native int uv_run(long pointer, int run_mode);

    /**
     * Will terminate the libuv loop.
     *
     * @param pointer the pointer to the loop.
     * @return the result, 0 = success, x < 0 = error.
     */
    private static native int uv_loop_close(long pointer);

}
