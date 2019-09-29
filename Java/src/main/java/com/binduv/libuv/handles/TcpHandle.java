package com.binduv.libuv.handles;

import com.binduv.IConnection;
import com.binduv.ConnectCallback;
import com.binduv.DisconnectCallback;
import com.binduv.ReadCallback;
import com.binduv.libuv.Libuv;
import com.binduv.libuv.handles.enums.UvHandleType;

public class TcpHandle extends UvHandle implements IConnection {

    private static int connectionId = 0;

    private long loopPointer;
    private String host;
    private int port;
    private int id;

    private ConnectCallback connectCallback = (connection) -> {
    };
    private DisconnectCallback disconnectCallback = (connection) -> {
    };
    private ReadCallback readCallback = (connection, bytes, readBytes) -> {
    };

    TcpHandle(long loopPointer, String host, int port) {
        super(loopPointer, UvHandleType.UV_TCP);

        connectionId++;

        this.loopPointer = loopPointer;
        this.host = host;
        this.port = port;
        this.id = connectionId;
    }

    /**
     * See the uv_tcp_init doc.
     */
    public void init() throws Exception {
        int result = uv_tcp_init(this.loopPointer, super.getHandlePointer());
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    /**
     * See the uv_tcp_bind doc.
     */
    public void bind() throws Exception {
        int result = uv_tcp_bind(super.getHandlePointer(), this.host, this.port);
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    /**
     * See the uv_listen doc.
     */
    public void listen() throws Exception {
        int result = uv_listen(super.getHandlePointer(), 100);
        if (result < 0) {
            throw new Exception(Libuv.uv_err_name(result));
        }
    }

    public void setConnectCallback(ConnectCallback connectCallback) {
        this.connectCallback = connectCallback;
    }

    public void setDisconnectCallback(DisconnectCallback disconnectCallback) {
        this.disconnectCallback = disconnectCallback;
    }

    public void setReadCallback(ReadCallback readCallback) {
        this.readCallback = readCallback;
    }

    /**
     * DO NOT CHANGE THE NAME OF THIS METHOD.
     * This method gets called from the C library.
     */
    private void onConnect() throws Exception {
        TcpHandle clientHandle = new TcpHandle(this.loopPointer, null, -1);
        int result = clientHandle.uv_tcp_init(clientHandle.loopPointer, clientHandle.getHandlePointer());
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

    /**
     * Will initialize the tcp handle and associate it with the given loop.
     *
     * @param loop_pointer the pointer to the loop handle.
     * @param tcp_pointer  the pointer to the tcp handle.
     * @return the result, 0 = success, x < 0 = error.
     */
    private native int uv_tcp_init(long loop_pointer, long tcp_pointer);

    /**
     * Will bind the tcp handle to the given host and port.
     *
     * @param tcp_pointer the pointer to the tcp handle.
     * @param host        the host to bind to.
     * @param port        the por tto bind to.
     * @return the result, 0 = success, x < 0 = error.
     */
    private static native int uv_tcp_bind(long tcp_pointer, String host, int port);

    /**
     * Will start listening for incoming connection with the given backlog.
     *
     * @param tcp_pointer the pointer to the server handle.
     * @param back_log    the backlog.
     * @return the result, 0 = success, x < 0 = error.
     */
    private static native int uv_listen(long tcp_pointer, int back_log);

    /**
     * Will accept the tcp client.
     *
     * @param server_pointer the pointer to the server handle.
     * @param client_pointer the pointer to the client handle.
     * @return the result, 0 = success, x < 0 = error.
     */
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
