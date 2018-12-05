package com.welson.reader.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.welson.reader.view.BaseReadView;

import java.util.ArrayList;

public class BookViewPagerAdapter extends PagerAdapter {

    private ArrayList<BaseReadView> baseReadViews;

    public BookViewPagerAdapter(ArrayList<BaseReadView> baseReadViews){
        this.baseReadViews = baseReadViews;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(baseReadViews.get(position% baseReadViews.size()));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(baseReadViews.get(position% baseReadViews.size()));
        return baseReadViews.get(position% baseReadViews.size());
    }


}
