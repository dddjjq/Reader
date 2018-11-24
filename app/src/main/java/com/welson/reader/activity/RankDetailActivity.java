package com.welson.reader.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.welson.reader.R;
import com.welson.reader.adapter.MainViewpagerAdapter;
import com.welson.reader.fragment.BaseFragment;
import com.welson.reader.fragment.RankDetailFragment;
import com.welson.reader.view.TriangleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TriangleIndicator indicator;
    private ViewPager viewPager;
    private String title;
    private String id;
    private String monthRank;
    private String totalRank;
    private RankDetailFragment dayRankFragment;
    private RankDetailFragment monthRankFragment;
    private RankDetailFragment totalRankFragment;
    private ArrayList<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_detail);
        initToolBar();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initToolBar(){
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        monthRank = getIntent().getStringExtra("monthRank");
        totalRank = getIntent().getStringExtra("totalRank");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initView(){
        indicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.view_pager);
        List<String> arr = Arrays.asList(getResources().getStringArray(R.array.array_rank_detail));
        indicator.setTabArray(arr);
    }

    private void initData(){
        fragments = new ArrayList<>();
        dayRankFragment = new RankDetailFragment();
        monthRankFragment = new RankDetailFragment();
        totalRankFragment = new RankDetailFragment();
        fragments.add(dayRankFragment);
        fragments.add(monthRankFragment);
        fragments.add(totalRankFragment);
        Bundle bundleDay = new Bundle();
        bundleDay.putString("id",id);
        dayRankFragment.setArguments(bundleDay);
        Bundle bundleMonth = new Bundle();
        bundleMonth.putString("id",monthRank);
        monthRankFragment.setArguments(bundleMonth);
        Bundle bundleTotal = new Bundle();
        bundleTotal.putString("id",totalRank);
        totalRankFragment.setArguments(bundleTotal);
        viewPager.setAdapter(new MainViewpagerAdapter(getSupportFragmentManager(),fragments));
        indicator.setViewPager(viewPager,0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
