package com.binduv.libuv.handles;

import com.binduv.libuv.Libuv;
import com.binduv.libuv.handles.enums.UvRunMode;

public class LoopHandle {

    private long pointer;

    static {
        System.loadLibrary("libjibuv");
    }

    public LoopHandle() {
        this.pointer = new_loop_handle();
    }

    public void run(UvRunMode runMode) throws Exception {
        int result = uv_run(this.pointer, runMode.getRunMode());
        if (result < 0) {
             throw new Exception(Libuv.uv_err_name(result));
        }
    }

    public AsyncHandle createAsyncHandle() {
        return new AsyncHandle(this.pointer);
    }

    public TcpHandle createTcpHandle(String host, int port) {
        return new TcpHandle(this.pointer, host, port);
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

}
