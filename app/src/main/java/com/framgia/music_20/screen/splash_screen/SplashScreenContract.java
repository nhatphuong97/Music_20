package com.framgia.music_20.screen.splash_screen;

public interface SplashScreenContract {
    interface View {
        void onShowSplashScreen();
    }

    interface Presenter {
        void runSplash();
    }
}
