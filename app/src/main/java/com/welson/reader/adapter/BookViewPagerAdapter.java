package com.welson.reader.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.welson.reader.view.ReadView;

import java.util.ArrayList;

public class BookViewPagerAdapter extends PagerAdapter {

    private ArrayList<ReadView> readViews;

    public BookViewPagerAdapter(ArrayList<ReadView> readViews){
        this.readViews = readViews;
    }

    @Override
    public int getCount() {
        return readViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(readViews.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(readViews.get(position));
        return readViews.get(position);
    }
}
