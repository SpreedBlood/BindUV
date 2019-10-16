package com.binduv.tests;

import com.binduv.*;
import com.binduv.libuv.handles.enums.UvRunMode;

public class AsyncHandler {

    public static void main(String[] args) {
        try {
            BindUvContext bindUvContext = new BindUvContext();
            HandleProvider handleProvider = bindUvContext.getHandleProvider();

            LoopHandle loopHandle = handleProvider.newLoop();
            AsyncHandle asyncHandle = handleProvider.newAsyncHandle(loopHandle);

            asyncHandle.init();
            asyncHandle.setCallback(async -> {
                System.out.println("Testing");
                try {
                    loopHandle.close();
                } catch (Exception ex) {

                }
            });
            asyncHandle.send();

            loopHandle.run(UvRunMode.UV_RUN_DEFAULT);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
