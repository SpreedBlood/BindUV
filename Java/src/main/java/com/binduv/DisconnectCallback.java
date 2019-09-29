package com.binduv;

@FunctionalInterface
public interface DisconnectCallback {

    void onDisconnect(IConnection connection);

}
