package com.binduv.libuv.handles.callbacks;

import com.binduv.libuv.handles.TcpHandle;

@FunctionalInterface
public interface ConnectionCallback {
    public void callback(TcpHandle tcpHandle);
}
