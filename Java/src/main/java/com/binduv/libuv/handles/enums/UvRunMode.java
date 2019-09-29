package com.binduv.libuv.handles.enums;

public enum UvRunMode {
    UV_RUN_DEFAULT(0),
    UV_RUN_ONCE(1),
    UV_RUN_NOWAIT(2);

    private final int runMode;

    UvRunMode(int runMode) {
        this.runMode = runMode;
    }

    public int getRunMode() {
        return this.runMode;
    }
}
