package com.framgia.music_20.screen.home;

import android.support.v4.view.ViewPager;
import android.view.View;

public class HomePagerTranformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        view.setAlpha(getAlpha(view, position));
    }

    private float getAlpha(View view, float position) {
        if (position < -1 || position > 1) {
            return 0;
        } else {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            float result =
                    MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA);
            return result;
        }
    }
}
