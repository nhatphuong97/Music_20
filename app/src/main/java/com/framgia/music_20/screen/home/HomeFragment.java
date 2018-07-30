package com.framgia.music_20.screen.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_20.R;

public class HomeFragment extends Fragment {

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
    }
}
