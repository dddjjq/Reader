package com.welson.reader.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.adapter.BookViewPagerAdapter;
import com.welson.reader.manager.SettingManager;
import com.welson.reader.util.SharedPreferenceUtil;
import com.welson.reader.view.ReadView;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BookViewPagerAdapter adapter;
    private String bookId;
    private ArrayList<ReadView> readViews;
    private int begin; //当前页起始位置
    private int currentChapter;
    private float textSize;
    private float space;
    private int lineCount;
    private int lineTextCount;

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            TextView textView = readViews.get(0).readContentText;
            textSize = textView.getTextSize(); //字体大小
            space = textView.getLineSpacingExtra();//行间距
            lineCount = (int)(textView.getHeight()/(textSize + space + textSize/5.3)) ;//行数
            //这里是因为textSize和text的高度不一样，但是设置includeFontPadding不起作用(textSize/5.3可以作为经验值)
            lineTextCount = (int)(textView.getWidth()/textSize);//每行字数
        }
    }

    private String getCalculateText(int currentChapter,String chapterText, int begin){
        int end;
        boolean isChapterEnd = false;
        StringBuilder result = new StringBuilder();
        int myLineCount = lineTextCount;
        for (int i=0;i<lineCount;i++){
            end = begin + myLineCount;
            String show = "";
            String chapter = "";
            if (chapterText.length() > end){ //分页
                show = chapterText.substring(begin,end);
            }else {
                show = chapterText.substring(begin,chapterText.length());
                isChapterEnd = true;
            }
            //Log.d("dingyl","show : " + show);
            if (show.contains("@")){
                chapter = show.substring(0,show.indexOf("@"));
                begin += 1;
                result.append(chapter);
                result.append("\n\u3000\u3000");
                myLineCount = lineTextCount - 2;
            }else {
                chapter = show;
                result.append(chapter);
                result.append("\n");
                myLineCount = lineTextCount;
            }
            begin += chapter.length();
            if (isChapterEnd){
                break;
            }
        }
        SettingManager.getInstance().saveReadProgress(getApplicationContext(),bookId,currentChapter,begin);
        return result.toString();
    }
}
