package com.binduv;

public interface HandleProvider {

    LoopHandle newLoop();

    ConnectionHandle newTcpHandle(LoopHandle loopHandle);

    AsyncHandle newAsyncHandle(LoopHandle loopHandle);

}
