package com.binduv;

import com.binduv.libuv.handles.DefaultHandleProvider;

public class BindUvContext {

    public HandleProvider getHandleProvider() {
        return new DefaultHandleProvider();
    }

}
