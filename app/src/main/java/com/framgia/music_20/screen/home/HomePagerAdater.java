package com.framgia.music_20.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.framgia.music_20.R;

public class HomePagerAdater extends PagerAdapter {

    private static final int[] RESOURCE = {
            R.drawable.ic_sontung, R.drawable.ic_huongtram, R.drawable.ic_gd, R.drawable.ic_iu
    };
    private LayoutInflater mLayoutInflater;

    public HomePagerAdater(Context context) {
        mLayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return RESOURCE.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.item_layout, container, false);
        ImageView imageView = view.findViewById(R.id.image_item);
        imageView.setImageResource(RESOURCE[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
