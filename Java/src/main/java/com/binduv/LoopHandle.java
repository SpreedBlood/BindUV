package com.binduv;

import com.binduv.libuv.handles.enums.UvRunMode;

import java.io.Closeable;

public interface LoopHandle extends Closeable {

    long getPointer();

    /**
     * Will run the loop handle with all the handles that are associated with it.
     * @param runMode the mode to run the loop in.
     * @throws Exception if the running failed.
     */
    void run(UvRunMode runMode) throws Exception;

}
