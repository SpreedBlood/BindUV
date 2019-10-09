package com.binduv;

import com.binduv.callbacks.ConnectCallback;
import com.binduv.callbacks.DisconnectCallback;
import com.binduv.callbacks.ReadCallback;

import java.io.Closeable;

public interface ConnectionHandle extends Closeable {

    int getConnectionId();

    /**
     * Will initialize the connection handle and associate it with the given loop.
     *
     * @throws Exception if the initialization failed.
     */
    void init() throws Exception;

    /**
     * Will bind the connection handle to the given host and port.
     *
     * @param host the host to bind to.
     * @param port the por tto bind to.
     * @throws Exception if the binding to the address failed.
     */
    void bind(String host, int port) throws Exception;

    /**
     * Will listening for connections, make sure to initialize the handle and bind to an address.
     *
     * @throws Exception if it failed to start listening for connections.
     */
    void listen() throws Exception;

    void setConnectCallback(ConnectCallback connectCallback);

    void setDisconnectCallback(DisconnectCallback disconnectCallback);

    void setReadCallback(ReadCallback readCallback);

}
