package com.netfruit.eventloops;

import java.io.Closeable;

public interface EventLoop extends Closeable {

    void execute(Runnable runnable);

}
