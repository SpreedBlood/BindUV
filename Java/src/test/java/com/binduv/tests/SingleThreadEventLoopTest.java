package com.binduv.tests;

import com.netfruit.eventloops.EventLoop;
import com.netfruit.eventloops.MultiThreadEventLoop;

public class SingleThreadEventLoopTest {

    public static void main(String[] args) {
        EventLoop eventLoop = new MultiThreadEventLoop(3);
        eventLoop.execute(() -> {
            System.out.println("Yay it works!");
        });
        eventLoop.execute(() -> {
            System.out.println("Yay it works!");
        });
        eventLoop.execute(() -> {
            System.out.println("Yay it works!");
        });
        eventLoop.execute(() -> {
            System.out.println("Yay it works!");
        });
        try {
            eventLoop.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
