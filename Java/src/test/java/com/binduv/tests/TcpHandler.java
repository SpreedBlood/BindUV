package com.binduv.tests;

import com.binduv.BindUvContext;
import com.binduv.ConnectionHandle;
import com.binduv.HandleProvider;
import com.binduv.LoopHandle;
import com.binduv.libuv.handles.enums.UvRunMode;

public class TcpHandler {
    public static void main(String[] args) {
        try {
            BindUvContext bindUvContext = new BindUvContext();
            HandleProvider handleProvider = bindUvContext.getHandleProvider();

            LoopHandle loopHandle = handleProvider.newLoop();
            ConnectionHandle connectionHandle = handleProvider.newTcpHandle(loopHandle);

            connectionHandle.setConnectCallback(connection -> {
                System.out.println("Connect");
                System.out.println(connection.getConnectionId());
            });

            connectionHandle.setDisconnectCallback(connection -> {
                System.out.println("Disconnected");
                System.out.println(connection.getConnectionId());
            });

            connectionHandle.setReadCallback((connection, bytes, read) -> {
                System.out.println("On reading");
                System.out.println(read);
            });

            connectionHandle.init();
            connectionHandle.bind("0.0.0.0", 30000);
            connectionHandle.listen();

            loopHandle.run(UvRunMode.UV_RUN_DEFAULT);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
