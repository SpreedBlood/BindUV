package com.binduv.callbacks;

import com.binduv.ConnectionHandle;

@FunctionalInterface
public interface ConnectCallback {

    void onConnect(ConnectionHandle connection);

}
