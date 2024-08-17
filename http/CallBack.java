package com.eastcompeace.lpa.sdk.http;

public interface CallBack<T> {
    void onError(Exception exc);

    void onSuccess(T t);
}
