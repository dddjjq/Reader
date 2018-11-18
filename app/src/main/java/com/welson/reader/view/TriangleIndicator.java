package com.welson.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class TriangleIndicator extends LinearLayout {

    private int textColorNotSelect;
    private int textColorSelected;
    private int indicatorHeight;
    private int tabCounts;
    private float textSize;
    private List<String> titles;
    private ViewPager viewPager;

    public TriangleIndicator(Context context) {
        super(context);
    }

    public TriangleIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TriangleIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context,AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TriangleIndicator);
        textColorNotSelect = ta.getColor(R.styleable.TriangleIndicator_text_color_not_select, Color.WHITE);
        textColorSelected = ta.getColor(R.styleable.TriangleIndicator_text_color_selected,Color.WHITE);
        indicatorHeight = ta.getInt(R.styleable.TriangleIndicator_indicator_height,0);
        tabCounts = ta.getInt(R.styleable.TriangleIndicator_tab_item_count,3);
        textSize = ta.getDimension(R.styleable.TriangleIndicator_text_size,0);
        ta.recycle();
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initTab();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    private void initTab(){
        if(titles != null && titles.size()>0){
            if(this.getChildCount() != 0){
                removeAllViews();
            }else {
                for (String title:titles){
                    addView(createTextView(title));
                }
            }
            initClickEvent();
        }
        Log.d("dingyl","initTab");
    }

    public void setViewPager(ViewPager viewPager,int position){
        this.viewPager = viewPager;
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setTabSelected(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setTabSelected(position);
    }

    private TextView createTextView(String title){
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getWidth()/tabCounts;
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        textView.setTextColor(textColorNotSelect);
        textView.setTextSize(DensityUtil.px2sp(getContext(),textSize));
        textView.setLayoutParams(lp);
        Log.d("dingyl","title : " + title);
        return textView;
    }

    public void setTabArray(List<String> titles){
        this.titles = titles;
    }

    private void initClickEvent(){
        setTabSelected(0);
        for (int i=0;i<getChildCount();i++){
            final int currentItem = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(currentItem);
                }
            });
        }
    }
    private void setTabSelected(int position){
        for (int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            if (view instanceof TextView){
                if (position == i){
                    ((TextView)view).setTextColor(textColorSelected);
                }else {
                    ((TextView)view).setTextColor(textColorNotSelect);
                }
            }
        }
    }
}
