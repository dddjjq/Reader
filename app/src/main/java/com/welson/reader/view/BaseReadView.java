package com.welson.reader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.reader.R;

public class BaseReadView extends RelativeLayout{

    private TextView readChapterText;
    public TextView readContentText;
    private TextView readTimeText;
    private TextView readCurrentChapterItemText;

    public BaseReadView(Context context) {
        super(context);
        init(context);
    }

    public BaseReadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.read_view_layout,this,true);
        readChapterText = findViewById(R.id.read_chapter);
        readContentText = findViewById(R.id.read_content);
        readTimeText = findViewById(R.id.read_current_time);
        readCurrentChapterItemText = findViewById(R.id.read_current_chapter);
    }

    public void setReadChapterText(String s){
        readChapterText.setText(s);
    }

    public void setReadContentText(String s){
        readContentText.setText(s);
    }

    public void setReadTimeText(String s){
        readTimeText.setText(s);
    }

    public void setReadCurrentChapterItemText(String s){
        readCurrentChapterItemText.setText(s);
    }

    public void setContentTextSize(int size){
        readContentText.setTextSize(size);
    }

    public int getMaxLine(){
        int height = readContentText.getHeight();
        int lineHeight = readContentText.getLineHeight();
        readContentText.getMaxLines();
        return height/lineHeight;
    }
}
