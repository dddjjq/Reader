package com.welson.reader.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.ArrayList;

public class ReadView extends RelativeLayout {

    private BaseReadView prePage,currPage,nextPage;
    private int preLeft;
    private int currLeft;
    private int nextLeft;
    private Scroller scroller;
    private float downX;
    private float moveX;
    private ArrayList<BaseReadView> readViews;

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
        prePage = new BaseReadView(context);
        currPage = new BaseReadView(context);
        nextPage = new BaseReadView(context);
        prePage.setReadContentText("1111111");
        currPage.setReadContentText("222222");
        nextPage.setReadContentText("333333");
        addView(prePage);
        addView(currPage);
        addView(nextPage);
        readViews = new ArrayList<>();
        readViews.add(prePage);
        readViews.add(currPage);
        readViews.add(nextPage);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        preLeft = -getWidth();
        currLeft = 0;
        nextLeft = getWidth();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        prePage.layout(-getWidth(),getTop(),0,getBottom());
        currPage.layout(0,getTop(),getWidth(),getBottom());
        nextPage.layout(getWidth(),getTop(),getWidth()*2,getBottom());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - downX;
                reLayout((int)dx);
                moveX += dx;
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                smoothNextPage();
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

    private void reLayout(int dx){
        preLeft += dx;
        currLeft += dx;
        nextLeft += dx;
        if (preLeft > 0){
            addPrePage();
            /*preLeft = 0;
            currLeft = getWidth();
            nextLeft = getWidth() * 2;*/
            restore();
        }
        if (nextLeft < 0){
            /*preLeft = -getWidth()*2;
            currLeft = -getWidth();
            nextLeft = 0;*/
            addNextPage();
            restore();
        }
        prePage.layout(preLeft,getTop(),preLeft+getWidth(),getBottom());
        currPage.layout(currLeft,getTop(),currLeft+getWidth(),getBottom());
        nextPage.layout(nextLeft,getTop(),nextLeft+getWidth(),getBottom());
    }

    private void addPrePage(){
        BaseReadView temp = currPage;
        currPage = prePage;
        prePage = nextPage;
        nextPage = temp;
    }

    private void addNextPage(){
        BaseReadView temp = currPage;
        currPage = nextPage;
        nextPage = prePage;
        prePage = temp;
    }

    private void restore(){
        preLeft = -getWidth();
        currLeft = 0;
        nextLeft = getWidth();
    }

    private void smoothNextPage(){
        float dx = 0;
        if (moveX >= 0){
            if (moveX > getWidth() / 2){
                scroller.startScroll(getScrollX(),0,(int)(moveX - getWidth()),0,getDuration(moveX - getWidth()));
                dx = moveX - getWidth();
                invalidate();
            }else {
                scroller.startScroll(getScrollX(),0,(int) moveX,0,getDuration(moveX));
                dx = moveX;
                invalidate();
            }
        }else {
            if (moveX < -getWidth() / 2){
                scroller.startScroll(getScrollX(),0,(int)(moveX + getWidth()),0,getDuration(moveX + getWidth()));
                dx = moveX + getWidth();
                invalidate();
            }else {
                scroller.startScroll(getScrollX(),0,(int) moveX,0,getDuration(moveX));
                dx = moveX;
                invalidate();
            }
        }
        /*preLeft += -dx;
        currLeft += -dx;
        nextLeft += -dx;*/
    }

    private int getDuration(float dx){
        return (int)(Math.abs(dx)/getWidth() * 1000);
    }

}
