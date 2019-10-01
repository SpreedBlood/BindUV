package com.binduv;

import com.binduv.callbacks.AsyncCallback;

public interface AsyncHandle {

    void init() throws Exception;

    void send() throws Exception;

    void setCallback(AsyncCallback callback);

}
