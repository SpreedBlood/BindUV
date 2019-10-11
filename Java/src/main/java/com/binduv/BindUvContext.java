package com.binduv;

import com.binduv.libuv.handles.DefaultHandleProvider;

public class BindUvContext {

    private static final HandleProvider handleProvider = new DefaultHandleProvider();

    public static HandleProvider getHandleProvider() {
        return handleProvider;
    }

}
