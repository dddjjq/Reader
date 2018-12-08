package com.welson.reader.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ReadOldView extends RelativeLayout {

    private BaseReadView prePage,currPage,nextPage;
    private int preLeft;
    private int currLeft;
    private int nextLeft;
    private Scroller scroller;
    private float downX;
    private float moveX;
    private ArrayList<BaseReadView> readViews;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_RIGHT = 2;
    private MoveHandler handler;
    private int handlerMove;

    public ReadOldView(Context context) {
        super(context);
        init(context);
    }

    public ReadOldView(Context context, @Nullable AttributeSet attrs) {
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
        handler = new MoveHandler(this);
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
                handler.removeMessages(ReadOldView.MOVE_RIGHT);
                handler.removeMessages(ReadOldView.MOVE_LEFT);
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - downX;
                reLayout((int)dx,false);
                moveX += dx;
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
               // smoothNextPage();
                if (moveX >= getWidth()/2){
                    handlerMove = (int)(getWidth()-moveX);
                    Message message = handler.obtainMessage();
                    message.what = MOVE_RIGHT;
                    message.arg1 = (int)(getWidth()-moveX);
                    handler.sendMessage(message);
                }else if (moveX > 0 && moveX < getWidth()/2){
                    handlerMove = (int)(moveX);
                    Message message = handler.obtainMessage();
                    message.what = MOVE_LEFT;
                    message.arg1 = (int)(moveX);
                    handler.sendMessage(message);
                }else if (moveX <= -getWidth()/2){
                    handlerMove = (int)(moveX);
                    Message message = handler.obtainMessage();
                    message.what = MOVE_LEFT;
                    message.arg1 = -(int)(moveX);
                    handler.sendMessage(message);
                }else if (moveX < 0 && moveX > -getWidth()/2){
                    handlerMove = (int)(getWidth() + moveX);
                    Message message = handler.obtainMessage();
                    message.what = MOVE_RIGHT;
                    message.arg1 = (int)(getWidth() + moveX);
                    handler.sendMessage(message);
                }
                moveX = 0;
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getFinalX(),0);
            postInvalidate();
        }
        super.computeScroll();
    }

    private void reLayout(int dx,boolean isHandler){
        if (true){
            preLeft += dx;
            currLeft += dx;
            nextLeft += dx;
            if (preLeft > 0){
                addPrePage();
                restore();
            }
            if (nextLeft < 0){
                addNextPage();
                restore();
            }
            prePage.layout(preLeft,getTop(),preLeft+getWidth(),getBottom());
            currPage.layout(currLeft,getTop(),currLeft+getWidth(),getBottom());
            nextPage.layout(nextLeft,getTop(),nextLeft+getWidth(),getBottom());
        }
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
        restore();
        /*preLeft += -dx;
        currLeft += -dx;
        nextLeft += -dx;*/
    }

    private int getDuration(float dx){
        return (int)(Math.abs(dx)/getWidth() * 1000);
    }

    static class MoveHandler extends Handler{

        WeakReference<ReadOldView> reference;
        ReadOldView readOldView;
        int step = 30;
        int sum1 = 0;
        int sum2 = 0;

        MoveHandler(ReadOldView mReadOldView){
            reference = new WeakReference<>(mReadOldView);
            readOldView = reference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ReadOldView.MOVE_LEFT){
                removeMessages(ReadOldView.MOVE_RIGHT);
                readOldView.reLayout(-step,true);
                sum1 += step;
                if (sum1 < readOldView.handlerMove){
                    sendEmptyMessageDelayed(ReadOldView.MOVE_LEFT,50);
                }else {
                    sum1 = 0;
                    removeMessages(ReadOldView.MOVE_LEFT);
                    readOldView.handlerMove=0;
                }
            }else if (msg.what == ReadOldView.MOVE_RIGHT){
                removeMessages(ReadOldView.MOVE_LEFT);
                readOldView.reLayout(step,true);
                sum2 += step;
                if (sum2 < readOldView.handlerMove){
                    sendEmptyMessageDelayed(ReadOldView.MOVE_RIGHT,50);
                }else {
                    sum2 = 0;
                    removeMessages(ReadOldView.MOVE_RIGHT);
                    readOldView.handlerMove=0;
                }
            }
        }

    }

}
