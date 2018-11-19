package com.welson.reader.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;

import com.welson.reader.R;
import com.welson.reader.adapter.MainViewpagerAdapter;
import com.welson.reader.fragment.BaseFragment;
import com.welson.reader.fragment.BookShelfFragment;
import com.welson.reader.fragment.CommunityFragment;
import com.welson.reader.fragment.DiscoverFragment;
import com.welson.reader.view.TriangleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private TriangleIndicator indicator;
    private ViewPager viewPager;
    private BookShelfFragment bookShelfFragment;
    private CommunityFragment communityFragment;
    private DiscoverFragment discoverFragment;
    private ArrayList<BaseFragment> fragments;
    private MainViewpagerAdapter viewpagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(mainToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initFragment();
    }

    private void initView(){
        mainToolbar = findViewById(R.id.main_toolbar);
        indicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.main_viewpager);
        initTab();
    }

    private void initTab(){
        int mainColor = getResources().getColor(R.color.colorMain);
        List<String> titles =  Arrays.asList(getResources().getStringArray(R.array.main_tab_text_arr)) ;
        indicator.setTabArray(titles);
    }

    private void initFragment(){
        fragments = new ArrayList<>();
        bookShelfFragment = new BookShelfFragment();
        communityFragment = new CommunityFragment();
        discoverFragment = new DiscoverFragment();
        fragments.add(bookShelfFragment);
        fragments.add(communityFragment);
        fragments.add(discoverFragment);
        viewpagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(viewpagerAdapter);
        indicator.setViewPager(viewPager,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
