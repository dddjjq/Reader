package com.welson.reader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.welson.reader.fragment.BaseFragment;

import java.util.ArrayList;

public class MainViewpagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments;

    public MainViewpagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
