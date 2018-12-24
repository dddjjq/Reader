package com.welson.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.welson.reader.R;

public class BookReadEntityLayout extends FrameLayout {

    private TextView title;
    private TextView content;
    private String titleText;
    private String contentText;

    public BookReadEntityLayout(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public BookReadEntityLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.book_read_entity_layout,this,true);
        title = findViewById(R.id.read_entity_title);
        content = findViewById(R.id.read_entity_content);
        if (attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.BookReadEntityLayout);
            titleText = ta.getString(R.styleable.BookReadEntityLayout_entityTitle);
            contentText = ta.getString(R.styleable.BookReadEntityLayout_entityContent);
            ta.recycle();
            title.setText(titleText);
            content.setText(contentText);
        }
    }

    public void setTitle(String s) {
        title.setText(s);
    }

    public void setContent(String s) {
        content.setText(s);
    }
}
