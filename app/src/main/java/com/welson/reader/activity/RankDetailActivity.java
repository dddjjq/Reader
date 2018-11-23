package com.welson.reader.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.welson.reader.R;
import com.welson.reader.view.TriangleIndicator;

import java.util.Arrays;
import java.util.List;

public class RankDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TriangleIndicator indicator;
    private ViewPager viewPager;

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
        String title = getIntent().getStringExtra("title");
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
    }

    private void initData(){
        List<String> arr = Arrays.asList(getResources().getStringArray(R.array.array_rank_detail));
        indicator.setTabArray(arr);
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
