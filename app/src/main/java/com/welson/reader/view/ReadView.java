package com.welson.reader.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ReadView extends LinearLayout {

    private BaseReadView prePage,currPage,nextPage;
    private int preLeft;
    private int currLeft;
    private int nextLeft;
    private Scroller scroller;
    private float downX;
    private float moveX;

    public ReadView(Context context) {
        super(context);
        init(context);
    }

    public ReadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        scroller = new Scroller(context);
        setOrientation(HORIZONTAL);
        prePage = new BaseReadView(context);
        currPage = new BaseReadView(context);
        nextPage = new BaseReadView(context);
        addView(prePage,0,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(currPage,1,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(nextPage,2,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        preLeft = -prePage.getWidth();
        currLeft = 0;
        nextLeft = nextPage.getWidth();
        prePage.layout(preLeft,prePage.getTop(),0,prePage.getBottom());
        currPage.layout(currLeft,currPage.getTop(),currPage.getWidth(),currPage.getBottom());
        nextPage.layout(nextPage.getWidth(),nextPage.getTop(),nextPage.getWidth()*2,nextPage.getBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);/*
        */
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX() - downX;
                Log.d("dingyl","moveX : " + moveX);
                prePage.layout((int)moveX,prePage.getTop(),prePage.getWidth()-(int)moveX,prePage.getBottom());
                break;
            case MotionEvent.ACTION_UP:
                moveX = 0;
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            postInvalidate();
        }
        super.computeScroll();
    }
}
