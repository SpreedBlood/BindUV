package com.binduv.callbacks;

import com.binduv.ConnectionHandle;

@FunctionalInterface
public interface DisconnectCallback {

    void onDisconnect(ConnectionHandle connection);

}
