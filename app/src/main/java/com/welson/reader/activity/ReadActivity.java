package com.welson.reader.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.adapter.BookViewPagerAdapter;
import com.welson.reader.manager.SettingManager;
import com.welson.reader.view.BaseReadView;
import com.welson.reader.view.readview.ReadFactory;
import com.welson.reader.view.readview.ReadView;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {

    private BookViewPagerAdapter adapter;
    private String bookId;
    private ReadView readView;
    private int begin; //当前页起始位置
    private int currentChapter;
    private float textSize;
    private float space;
    private int lineCount;
    private int lineTextCount;
    private ReadFactory factory;

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
        readView = findViewById(R.id.read_view);
        factory = new ReadFactory(this);
        readView.setPageFactory(factory);
    }

    private void initData(){
        bookId = getIntent().getStringExtra("bookId");
        currentChapter = getIntent().getIntExtra("currentChapter",0);
    }

    private void addListener(){
    }

    private void loadCurrentPageData(){

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            /*TextView textView = baseReadViews.get(0).readContentText;
            textSize = textView.getTextSize(); //字体大小
            space = textView.getLineSpacingExtra();//行间距
            lineCount = (int)(textView.getHeight()/(textSize + space + textSize/5.3)) ;//行数
            //这里是因为textSize和text的高度不一样，但是设置includeFontPadding不起作用(textSize/5.3可以作为经验值)
            lineTextCount = (int)(textView.getWidth()/textSize);//每行字数*/
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
