package com.framgia.music_20.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.music_20.R;
import com.framgia.music_20.utils.Constant;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,ViewPager.OnPageChangeListener {

    BottomNavigationView mNavigationView = null;
    private ViewPager mViewPager;
    private MainActivityAdapter mActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);
        mViewPager = findViewById(R.id.viewpager);
        mActivityAdapter = new MainActivityAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mActivityAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                mViewPager.setCurrentItem(Constant.Tab.TAB_HOME);
                return true;
            case R.id.item_mymusic:
                mViewPager.setCurrentItem(Constant.Tab.TAB_MY_MUSIC);
                return true;
            case R.id.item_artist:
                mViewPager.setCurrentItem(Constant.Tab.TAB_ARTIST);
                return true;
            case R.id.item_search:
                mViewPager.setCurrentItem(Constant.Tab.TAB_SEARCH);
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
