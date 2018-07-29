package com.framgia.music_20.screen.splash_screen;

import com.framgia.music_20.utils.Constant;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {
    private SplashScreenContract.View mView;

    public void setView(SplashScreenContract.View view) {
        mView = view;
    }

    @Override
    public void runSplash() {
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Constant.TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mView.onShowSplashScreen();
                }
            }
        });
        mThread.start();
    }
}
