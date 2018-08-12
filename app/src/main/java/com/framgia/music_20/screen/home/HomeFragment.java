package com.framgia.music_20.screen.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.framgia.music_20.R;
import com.framgia.music_20.screen.list_song.ListSongFragment;
import com.framgia.music_20.utils.Constant;

public class HomeFragment extends Fragment implements View.OnClickListener {

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
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.button_music:
                fragment = ListSongFragment.getGenreFragment(Constant.GENRES_MUSIC);
                break;
            case R.id.button_audio:
                fragment = ListSongFragment.getGenreFragment(Constant.GENRES_AUDIO);
                break;
            case R.id.button_classic:
                fragment = ListSongFragment.getGenreFragment(Constant.GENRES_CLASSIC);
                break;
            case R.id.button_rock:
                fragment = ListSongFragment.getGenreFragment(Constant.GENRES_ROCK);
                break;
            case R.id.button_ambient:
                fragment = ListSongFragment.getGenreFragment(Constant.GENRES_AMBIENT);
                break;
            case R.id.button_country:
                fragment = ListSongFragment.getGenreFragment(Constant.GENRES_COUNTRY);
                break;
        }
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down);
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
