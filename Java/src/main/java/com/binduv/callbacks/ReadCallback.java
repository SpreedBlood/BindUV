package com.binduv.callbacks;

import com.binduv.ConnectionHandle;

@FunctionalInterface
public interface ReadCallback {

    void onRead(ConnectionHandle connection, byte[] bytes, long read);

}
