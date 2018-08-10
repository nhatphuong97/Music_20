package com.framgia.music_20.data.source;

import com.framgia.music_20.data.model.Song;
import java.util.List;

public interface DataCallbackLocate {
    void onSuccess(List<Song> songList);

    void onFail(Exception e);
}
