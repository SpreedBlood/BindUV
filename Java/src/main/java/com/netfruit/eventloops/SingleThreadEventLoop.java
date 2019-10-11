package com.netfruit.eventloops;

import com.binduv.AsyncHandle;
import com.binduv.BindUvContext;
import com.binduv.HandleProvider;
import com.binduv.LoopHandle;
import com.binduv.libuv.handles.enums.UvRunMode;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreadEventLoop implements EventLoop {

    private static final int NOT_STARTED = 0;
    private static final int STARTED = 1;
    private static final int SHUTDOWN = 2;

    private LoopHandle loopHandle;
    private AsyncHandle asyncHandle;
    private Thread thread;
    private Queue<Runnable> taskQueue;

    private AtomicInteger state;

    public SingleThreadEventLoop() {
        HandleProvider handleProvider = BindUvContext.getHandleProvider();
        this.loopHandle = handleProvider.newLoop();
        this.asyncHandle = handleProvider.newAsyncHandle(loopHandle);
        this.taskQueue = new ConcurrentLinkedQueue<>();
        this.thread = new Thread(this::threadCallback);
        this.thread.start();
        this.state = new AtomicInteger(NOT_STARTED);
    }

    private void runAllTasks() {
        while (true) {
            Runnable task = this.taskQueue.poll();
            if (task == null) break;

            task.run();
        }
    }

    private void asyncCallback(AsyncHandle asyncHandle) {
        if (this.state.get() == SHUTDOWN) {
            this.runAllTasks();
            try {
                this.asyncHandle.close();
                this.loopHandle.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            this.runAllTasks();
        }
    }

    private void threadCallback() {
        try {
            this.asyncHandle.init();
            this.asyncHandle.setCallback(this::asyncCallback);
            this.state.set(STARTED);
            this.loopHandle.run(UvRunMode.UV_RUN_DEFAULT);
        } catch (Exception ex) {
            System.out.println("Failed to setup single threaded event loop");
            ex.printStackTrace();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            this.taskQueue.add(runnable);
            this.asyncHandle.send();
        } catch (Exception ex) {
            System.out.println("Failed to execute task: " + runnable);
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        this.state.set(SHUTDOWN);
        try {
            this.asyncHandle.send();
        } catch (Exception ex) {
            System.out.println("Failed to send async. and close single threaded event loop.");
            ex.printStackTrace();
        }
    }
}
