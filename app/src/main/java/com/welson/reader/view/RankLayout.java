package com.welson.reader.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.activity.RankDetailActivity;
import com.welson.reader.util.GlideUtil;

public class RankLayout extends LinearLayout {

    private int iconId;
    private String itemString;
    private ImageView image;
    private TextView text;

    public RankLayout(Context context) {
        super(context);
    }

    public RankLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.rank_item_layout,this,true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RankLayout);
        iconId = ta.getResourceId(R.styleable.RankLayout_icon,0);
        itemString = ta.getString(R.styleable.RankLayout_name);
        ta.recycle();
        image = findViewById(R.id.rank_item_image);
        text = findViewById(R.id.rank_item_text);
        image.setImageResource(iconId);
        text.setText(itemString);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RankDetailActivity.class);
                intent.putExtra("title",itemString);
                context.startActivity(intent);
            }
        });
    }

    public void setImage(String url){
        GlideUtil.loadImage(getContext(),url,image);
    }

    public void setItemString(String s){
        itemString = s;
        text.setText(itemString);
    }

}
