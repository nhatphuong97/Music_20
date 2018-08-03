package com.framgia.music_20.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.framgia.music_20.R;
import com.framgia.music_20.screen.list_song.ListSongActivity;
import com.framgia.music_20.utils.Constant;

public class HomeFragment extends Fragment implements View.OnClickListener{

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        HomePagerAdater homePagerAdater = new HomePagerAdater(getActivity());
        viewPager.setAdapter(homePagerAdater);
        viewPager.setPageTransformer(true, new HomePagerTranformer());
        ImageButton buttonMusic = view.findViewById(R.id.button_music);
        ImageButton buttonAudio = view.findViewById(R.id.button_audio);
        ImageButton buttonClassic = view.findViewById(R.id.button_classic);
        ImageButton buttonRock = view.findViewById(R.id.button_rock);
        ImageButton buttonAmbient = view.findViewById(R.id.button_ambient);
        ImageButton buttonCountry = view.findViewById(R.id.button_country);
        buttonMusic.setOnClickListener(this);
        buttonAudio.setOnClickListener(this);
        buttonClassic.setOnClickListener(this);
        buttonRock.setOnClickListener(this);
        buttonAmbient.setOnClickListener(this);
        buttonCountry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_music:
                startActivity(new Intent(
                        ListSongActivity.newInstance(getContext(), Constant.GENRES_MUSIC)));
                break;
            case R.id.button_audio:
                startActivity(new Intent(
                        ListSongActivity.newInstance(getContext(), Constant.GENRES_AUDIO)));
                break;
            case R.id.button_classic:
                startActivity(new Intent(
                        ListSongActivity.newInstance(getContext(), Constant.GENRES_CLASSIC)));
                break;
            case R.id.button_rock:
                startActivity(new Intent(
                        ListSongActivity.newInstance(getContext(), Constant.GENRES_ROCK)));
                break;
            case R.id.button_ambient:
                startActivity(new Intent(
                        ListSongActivity.newInstance(getContext(), Constant.GENRES_AMBIENT)));
                break;
            case R.id.button_country:
                startActivity(new Intent(
                        ListSongActivity.newInstance(getContext(), Constant.GENRES_COUNTRY)));
                break;
        }
    }
}
