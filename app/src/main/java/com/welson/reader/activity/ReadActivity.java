package com.welson.reader.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.welson.reader.R;
import com.welson.reader.adapter.BookViewPagerAdapter;

public class ReadActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BookViewPagerAdapter adapter;
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initView();
        initData();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.window_exit_anim);
    }

    private void initView(){
        viewPager = findViewById(R.id.read_viewPager);
    }

    private void initData(){
        bookId = getIntent().getStringExtra("bookId");
    }

}
