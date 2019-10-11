package com.netfruit.eventloops;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadEventLoop implements EventLoop {

    private AtomicInteger atomicInteger = new AtomicInteger();

    private EventLoop[] eventLoops;

    public MultiThreadEventLoop(int thread) {
        this.eventLoops = new EventLoop[thread];
        for (int i = 0; i < thread; i++) {
            this.eventLoops[i] = new SingleThreadEventLoop();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        this.getNextEventLoop().execute(runnable);
    }

    @Override
    public void close() throws IOException {
        for (EventLoop eventLoop : this.eventLoops) {
            eventLoop.close();
        }
    }

    private EventLoop getNextEventLoop() {
        return this.eventLoops[atomicInteger.getAndIncrement() & this.eventLoops.length - 1];
    }
}
