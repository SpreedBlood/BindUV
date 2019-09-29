package com.binduv.tests;

import com.binduv.libuv.handles.LoopHandle;
import com.binduv.libuv.handles.TcpHandle;
import com.binduv.libuv.handles.enums.UvRunMode;

public class TcpHandler {
    public static void main(String[] args) {
        try {
            LoopHandle loopHandle = new LoopHandle();
            TcpHandle tcpHandle = loopHandle.createTcpHandle("0.0.0.0", 30000);

            tcpHandle.setConnectCallback(connection -> {
                System.out.println("Connect");
                System.out.println(connection.getConnectionId());
            });
            tcpHandle.setDisconnectCallback(connection -> {
                System.out.println("Disconnected");
                System.out.println(connection.getConnectionId());
            });
            tcpHandle.setReadCallback((connection, bytes, read) -> {
                System.out.println("On reading");
                System.out.println(read);
            });

            tcpHandle.init();
            tcpHandle.bind();
            tcpHandle.listen();

            loopHandle.run(UvRunMode.UV_RUN_DEFAULT);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
