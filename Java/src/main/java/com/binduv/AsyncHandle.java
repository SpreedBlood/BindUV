package com.binduv;

import com.binduv.callbacks.AsyncCallback;

import java.io.Closeable;

public interface AsyncHandle extends Closeable {

    void init() throws Exception;

    void send() throws Exception;

    void setCallback(AsyncCallback callback);

}
