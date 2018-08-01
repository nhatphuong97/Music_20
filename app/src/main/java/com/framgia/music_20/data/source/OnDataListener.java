package com.framgia.music_20.data.source;

public interface OnDataListener {
    void onSuccess(String data);

    void onFail(Exception e);
}
