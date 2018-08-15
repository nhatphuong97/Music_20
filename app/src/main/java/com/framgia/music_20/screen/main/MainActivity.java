package com.framgia.music_20.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.framgia.music_20.R;
import com.framgia.music_20.screen.play_song.PlayMusicFragment;
import com.framgia.music_20.utils.Constant;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    BottomNavigationView mNavigationView = null;
    private ViewPager mViewPager;
    private boolean doubleClicktoExit;
    ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(this);
        mViewPager = findViewById(R.id.viewpager_home);
        mConstraintLayout = findViewById(R.id.layout_mini);
        mConstraintLayout.setVisibility(View.GONE);
        MainActivityAdapter activityAdapter = new MainActivityAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(activityAdapter);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Fragment frag : fragmentManager.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
            }
        }
        if (doubleClicktoExit) {
            moveTaskToBack(true);
            return;
        }
        this.doubleClicktoExit = true;
        getFragmentManager().popBackStack();
        Toast.makeText(this, R.string.text_exit, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClicktoExit = false;
            }
        }, Constant.TIME_RESPONE);
    }
}
