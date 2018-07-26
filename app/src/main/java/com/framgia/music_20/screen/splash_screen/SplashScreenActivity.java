package com.framgia.music_20.screen.splash_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import com.framgia.music_20.R;
import com.framgia.music_20.screen.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {
    private ProgressBar mProgressBar;
    private SplashScreenPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mProgressBar = findViewById(R.id.progressBar);
        mPresenter = new SplashScreenPresenter();
        mPresenter.setView(this);
        mPresenter.runSplash();
    }

    @Override
    public void onShowSplashScreen() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
