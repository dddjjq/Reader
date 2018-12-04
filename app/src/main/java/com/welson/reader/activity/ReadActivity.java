package com.welson.reader.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.welson.reader.R;
import com.welson.reader.adapter.BookViewPagerAdapter;
import com.welson.reader.view.ReadView;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BookViewPagerAdapter adapter;
    private String bookId;
    private ArrayList<ReadView> readViews;
    private int begin; //当前页起始位置
    private int currentChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initView();
        initData();
        addListener();
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
        currentChapter = getIntent().getIntExtra("currentChapter",0);
        readViews = new ArrayList<>();
        for (int i=0;i<5;i++){
            readViews.add(new ReadView(this));
        }
        adapter = new BookViewPagerAdapter(readViews);
        viewPager.setAdapter(adapter);
    }

    private void addListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("dingyl","onPageScrollStateChanged position : " + position);
                readViews.get(2).setReadContentText("test");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE: // 停止滑动
                        Log.d("dingyl","SCROLL_STATE_IDLE");
                        //viewPager.setCurrentItem(1,false);
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.d("dingyl","SCROLL_STATE_DRAGGING");
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        Log.d("dingyl","SCROLL_STATE_SETTLING");
                        break;
                }
            }
        });
    }

    private void loadCurrentPageData(){
        ReadView readView = readViews.get(viewPager.getCurrentItem());

    }
}
