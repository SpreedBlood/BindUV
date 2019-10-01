package com.binduv.libuv.handles;

import com.binduv.ConnectionHandle;
import com.binduv.callbacks.ConnectCallback;
import com.binduv.callbacks.DisconnectCallback;
import com.binduv.callbacks.ReadCallback;
import com.binduv.libuv.handles.enums.UvHandleType;

class Tcp extends UvHandle implements ConnectionHandle {

    private static int connectionId = 0;

    private String host;
    private int port;
    private int id;

    private ConnectCallback connectCallback = (connection) -> {
    };
    private DisconnectCallback disconnectCallback = (connection) -> {
    };
    private ReadCallback readCallback = (connection, bytes, readBytes) -> {
    };

    Tcp(long loopPointer) {
        super(loopPointer, UvHandleType.UV_TCP);

        connectionId++;
        this.id = connectionId;
    }

    @Override
    public void init() throws Exception {
        int result = uv_tcp_init(this.getLoopPointer(), super.getHandlePointer());
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    @Override
    public void bind(String host, int port) throws Exception {
        int result = uv_tcp_bind(super.getHandlePointer(), host, port);
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    @Override
    public void listen() throws Exception {
        int result = uv_listen(super.getHandlePointer(), 100);
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    @Override
    public void setConnectCallback(ConnectCallback connectCallback) {
        this.connectCallback = connectCallback;
    }

    @Override
    public void setDisconnectCallback(DisconnectCallback disconnectCallback) {
        this.disconnectCallback = disconnectCallback;
    }

    @Override
    public void setReadCallback(ReadCallback readCallback) {
        this.readCallback = readCallback;
    }

    /**
     * DO NOT CHANGE THE NAME OF THIS METHOD.
     * This method gets called from the C library.
     */
    private void onConnect() throws Exception {
        Tcp clientHandle = new Tcp(this.getLoopPointer());
        int result = clientHandle.uv_tcp_init(clientHandle.getLoopPointer(), clientHandle.getHandlePointer());
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }

        result = uv_accept(super.getHandlePointer(), clientHandle.getHandlePointer());
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }

        clientHandle.setDisconnectCallback(this.disconnectCallback);
        clientHandle.setReadCallback(this.readCallback);

        result = uv_read_start(clientHandle.getHandlePointer());
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }

        this.connectCallback.onConnect(clientHandle);
    }

    /**
     * DO NOT CHANGE THE NAME OF THIS METHOD.
     * This method gets called from the C library.
     *
     * @param array the data that was read.
     * @param nread the length of the data that was read.
     */
    private void onRead(byte[] array, long nread) {
        this.readCallback.onRead(this, array, nread);
    }

    /**
     * DO NOT CHANGE THE NAME OF THIS METHOD.
     * This method gets called from the C library.
     */
    private void onDisconnect() {
        this.disconnectCallback.onDisconnect(this);
    }

    private native int uv_tcp_init(long loop_pointer, long tcp_pointer);

    private static native int uv_tcp_bind(long tcp_pointer, String host, int port);

    private static native int uv_listen(long tcp_pointer, int back_log);

    private static native int uv_accept(long server_pointer, long client_pointer);

    /**
     * Will start reading data from the connection.
     *
     * @param client_pointer the pointer to the client handle.
     * @return the result, 0 = success, x < 0 = error.
     */
    private static native int uv_read_start(long client_pointer);

    @Override
    public int getConnectionId() {
        return this.id;
    }
}
