package com.welson.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Paint mPaint;
    private Path mPath;
    private int width;
    private int triangleHeight;
    private int triangleWidth;
    private int initTranslationX;
    private int translationX;

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
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FFFFFFFF"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initTab();
        initTriangle();
        width = getWidth() - getPaddingStart() - getPaddingEnd();
        initTranslationX = (getWidth() - getPaddingStart() - getPaddingEnd())/tabCounts/2 - triangleWidth/2 + getPaddingStart();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(initTranslationX + translationX,getHeight()+1);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    private void initTriangle(){
        mPath = new Path();
        triangleWidth = (getWidth() - getPaddingStart() - getPaddingEnd())/tabCounts/8;
        triangleHeight = (int)(triangleWidth/2/Math.sqrt(2));
        mPath.moveTo(0,0);
        mPath.lineTo(triangleWidth,0);
        mPath.lineTo(triangleWidth/2,-triangleHeight);
        mPath.close();
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
    }

    public void setViewPager(ViewPager viewPager,int position){
        this.viewPager = viewPager;
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                scroll(i,v);
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

    private void scroll(int position,float offset){
        translationX = (int)((getWidth() - getPaddingStart() - getPaddingEnd())/tabCounts*(position + offset));
        invalidate();
    }

    private TextView createTextView(String title){
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = (getWidth() - getPaddingStart() - getPaddingEnd())/tabCounts;
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        textView.setTextColor(textColorNotSelect);
        textView.setTextSize(DensityUtil.px2sp(getContext(),textSize));
        textView.setLayoutParams(lp);
        return textView;
    }

    //TODO to delete
    private Button createButtonTest(){
        Button button = new Button(getContext());
        button.setText("test");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        button.setLayoutParams(lp);
        return button;
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
