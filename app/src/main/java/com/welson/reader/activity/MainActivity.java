package com.welson.reader.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;

import com.welson.reader.R;
import com.welson.reader.adapter.MainViewpagerAdapter;
import com.welson.reader.constant.Constants;
import com.welson.reader.fragment.BaseFragment;
import com.welson.reader.fragment.BookShelfFragment;
import com.welson.reader.fragment.CommunityFragment;
import com.welson.reader.fragment.DiscoverFragment;
import com.welson.reader.fragment.GenderSelectFragment;
import com.welson.reader.util.SharedPreferenceUtil;
import com.welson.reader.view.TriangleIndicator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar mainToolbar;
    private TriangleIndicator indicator;
    private ViewPager viewPager;
    private BookShelfFragment bookShelfFragment;
    private CommunityFragment communityFragment;
    private DiscoverFragment discoverFragment;
    private ArrayList<BaseFragment> fragments;
    private MainViewpagerAdapter viewpagerAdapter;
    private GenderSelectFragment genderSelectFragment;
    private static final int SHOW_GENDER_SELECT = 0x01;
    private MainHandler handler;
    private boolean isSelectGender = false;
    private int currentPage = 0;

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
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollToPage(currentPage);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPage",viewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int currentPage = savedInstanceState.getInt("currentPage");
        viewPager.setCurrentItem(currentPage);
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
        genderSelectFragment = new GenderSelectFragment();//这个不需要加入ArrayList
        fragments.add(bookShelfFragment);
        fragments.add(communityFragment);
        fragments.add(discoverFragment);
        viewpagerAdapter = new MainViewpagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(viewpagerAdapter);
        indicator.setViewPager(viewPager,0);

    }

    private void initData(){
        handler = new MainHandler(this);
        isSelectGender = SharedPreferenceUtil.getBoolean(this, Constants.SP_IS_SELECT_GENDER,false);
        if (!isSelectGender){
            handler.sendEmptyMessageDelayed(SHOW_GENDER_SELECT,500);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    static class MainHandler extends Handler{
        WeakReference<MainActivity> reference;
        MainActivity activity;

        MainHandler(MainActivity activity) {
            reference = new WeakReference<>(activity);
            this.activity = reference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOW_GENDER_SELECT:
                    activity.genderSelectFragment.show(activity.getSupportFragmentManager(),"gender");
                    break;
            }
        }
    }

    public void setCurrentPage(int position){
        currentPage = position;
    }

    public void scrollToPage(int position){
        if (position > 2){
            position = 2;
        }else if (position < 0){
            position = 0;
        }
        viewPager.setCurrentItem(position);
        Log.d("dingyl","position : " + position);
    }

    public void firstLoadData(boolean isMale){
        bookShelfFragment.firstLoadData(isMale);
    }
}
