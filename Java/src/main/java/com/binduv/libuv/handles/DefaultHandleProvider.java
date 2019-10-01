package com.binduv.libuv.handles;

import com.binduv.AsyncHandle;
import com.binduv.ConnectionHandle;
import com.binduv.HandleProvider;
import com.binduv.LoopHandle;

public class DefaultHandleProvider implements HandleProvider {
    @Override
    public LoopHandle newLoop() {
        return new Loop();
    }

    @Override
    public ConnectionHandle newTcpHandle(LoopHandle loopHandle) {
        return new Tcp(loopHandle.getPointer());
    }

    @Override
    public AsyncHandle newAsyncHandle(LoopHandle loopHandle) {
        return new Async(loopHandle.getPointer());
    }
}
