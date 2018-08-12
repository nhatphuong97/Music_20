package com.framgia.music_20.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.framgia.music_20.screen.home.HomeFragment;
import com.framgia.music_20.screen.my_music.MyMusicFragment;
import com.framgia.music_20.screen.search.SearchFragment;
import com.framgia.music_20.utils.Constant;

public class MainActivityAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 3;

    public MainActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constant.Tab.TAB_HOME:
                return HomeFragment.newInstance();
            case Constant.Tab.TAB_MY_MUSIC:
                return MyMusicFragment.newInstance();
            case Constant.Tab.TAB_SEARCH:
                return SearchFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
