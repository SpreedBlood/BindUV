package com.binduv;

@FunctionalInterface
public interface ReadCallback {

    void onRead(IConnection connection, byte[] bytes, long read);

}
