package com.framgia.music_20.screen.my_music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_20.R;

public class MyMusicFragment extends Fragment {
    private View mView;

    public static MyMusicFragment newInstance() {
        MyMusicFragment fragment = new MyMusicFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_mymusic, container, false);
        return mView;
    }
}
