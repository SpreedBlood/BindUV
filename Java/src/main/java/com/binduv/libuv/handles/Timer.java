package com.binduv.libuv.handles;

import com.binduv.libuv.handles.enums.UvHandleType;

class Timer extends UvHandle {
    Timer(long loopPointer, UvHandleType uvHandleType) {
        super(loopPointer, uvHandleType);
    }
}
