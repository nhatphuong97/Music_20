package com.framgia.music_20.data.source;

public interface DataCallBack<T> {
    void onSuccess(T data);

    void onFail(Exception e);
}
