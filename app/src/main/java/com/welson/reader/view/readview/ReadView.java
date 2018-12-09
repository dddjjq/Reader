package com.welson.reader.view.readview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Scroller;

import com.welson.reader.entity.TouchPoint;
import com.welson.reader.util.ViewUtils;

public class ReadView extends View {

    private static int defWidth = 600;
    private static int defHeight = 1000;
    private int viewWidth;
    private int viewHeight;
    private Bitmap previousBitmap;
    private Bitmap currentBitmap;
    private Bitmap nextBitmap;
    private Scroller scroller;
    private float scrollLeft; // 滑动页的左边
    private int downX;
    private int touchState;
    private static final int TOUCH_MIDDLE = 0;
    private static final int TOUCH_LEFT = 1;
    private static final int TOUCH_RIGHT = 2;
    private int pageState;
    private static final int PAGE_STAY = 0;
    private static final int PAGE_PRIV = 1;
    private static final int PAGE_NEXT = 2;
    private PageFactory factory;
    private TouchPoint touchPoint;
    private GradientDrawable gradientDrawable;
    private int scrollTime = 300;
    private float moveX;
    private int currentPage = 1;

    public ReadView(Context context) {
        super(context);
        init(context);
    }

    public ReadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        touchState = TOUCH_RIGHT;
        pageState = PAGE_STAY;
        scroller = new Scroller(context);
        scrollLeft = 0;
        touchPoint = new TouchPoint(-1,-1);
        int[] mBackShadowColors = new int[] { 0x66000000,0x00000000};
        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors);
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = ViewUtils.getMeasuredSize(widthMeasureSpec,defWidth);
        viewHeight = ViewUtils.getMeasuredSize(heightMeasureSpec,defHeight);
        setMeasuredDimension(viewWidth,viewHeight);
        previousBitmap = Bitmap.createBitmap(viewWidth,viewHeight, Bitmap.Config.ARGB_8888);
        currentBitmap = Bitmap.createBitmap(viewWidth,viewHeight, Bitmap.Config.ARGB_8888);
        nextBitmap = Bitmap.createBitmap(viewWidth,viewHeight, Bitmap.Config.ARGB_8888);
        previousBitmap.eraseColor(Color.WHITE);
        currentBitmap.eraseColor(Color.WHITE);
        nextBitmap.eraseColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (factory != null){
            int state = Math.abs(currentPage)%3;
            if(touchPoint.getX() == -1 && touchPoint.getY() == -1){
                if (state == 1){
                    drawCurrentPage(canvas);
                }else if (state == 0){
                    drawPreviousPage(canvas);
                }else if (state == 2){
                    drawNextPage(canvas);
                }
                pageState = PAGE_STAY;
            }else {
                Log.d("dingyl","state " + state);
                if (touchState == TOUCH_RIGHT){
                    //drawCurrentPage(canvas);
                    //drawNextPage(canvas);
                    drawShadow(canvas);
                }else if (touchState == TOUCH_LEFT){
                    //drawNextPage(canvas);
                    //drawCurrentPage(canvas);
                    drawShadow(canvas);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (pageState == PAGE_STAY){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    downX = (int)x;
                    if (downX < viewWidth/3){
                        touchState = TOUCH_LEFT;
                        factory.drawCurrentBitmap(currentBitmap);
                        factory.drawNextBitmap(nextBitmap);
                    }else if (downX > viewWidth/3*2){
                        factory.drawPreviousBitmap(previousBitmap);
                        factory.drawCurrentBitmap(currentBitmap);
                        touchState = TOUCH_RIGHT;
                    }else {
                        touchState = TOUCH_MIDDLE;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveX = x - downX;
                    if (touchState == TOUCH_LEFT){
                        scrollPage(x,y);
                    }else if ((touchState == TOUCH_RIGHT)){
                        scrollPage(x,y);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    autoScroll();
                    moveX = 0;
                    break;
            }
        }
        return true;

    }

    public void setPageFactory(final PageFactory pageFactory){
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                factory = pageFactory;
                factory.drawCurrentBitmap(currentBitmap);
                postInvalidate();
                return true;
            }
        });
    }

    private void scrollPage(float x,float y){
        touchPoint.setX(x);
        touchPoint.setY(y);
        if (touchState == TOUCH_RIGHT){
            scrollLeft = touchPoint.getX() - downX;
        }else if (touchState == TOUCH_LEFT){
            scrollLeft = touchPoint.getX() -downX - viewWidth;
        }
        if (scrollLeft > 0){
            scrollLeft = 0;
        }
        postInvalidate();
    }

    private void drawCurrentPage(Canvas canvas){
        if (touchState == TOUCH_RIGHT){
            canvas.drawBitmap(currentBitmap,0,0,null);
        }else if (touchState == TOUCH_LEFT){
            canvas.drawBitmap(currentBitmap,scrollLeft,0,null);
        }
    }

    private void drawPreviousPage(Canvas canvas){
        canvas.drawBitmap(previousBitmap,scrollLeft,0,null);
    }

    private void drawNextPage(Canvas canvas){
        canvas.drawBitmap(nextBitmap,0,0,null);
    }

    private void drawShadow(Canvas canvas){
        int left = (int)(viewWidth + scrollLeft);
        gradientDrawable.setBounds(left,0,left + 30,viewHeight);
        gradientDrawable.draw(canvas);
    }

    private void autoScroll(){
        if (Math.abs(moveX) < viewWidth/3){
            startCancelAnim();
            return;
        }
        if (touchState == TOUCH_LEFT){
            autoScrollToPreviousPage();
        }else if (touchState == TOUCH_RIGHT){
            autoScrollToNextPage();
        }
    }

    private void autoScrollToNextPage(){
        pageState = PAGE_NEXT;
        int dx,dy;
        dx = -(int)(viewWidth + scrollLeft);
        dy = (int)(touchPoint.getY());
        int time = (int)((1+scrollLeft/viewWidth) * scrollTime);
        scroller.startScroll((int)(viewWidth+scrollLeft),(int)touchPoint.getY(),dx,dy,time);
        invalidate();
        currentPage ++;
    }

    private void autoScrollToPreviousPage(){
        pageState = PAGE_PRIV;
        int dx,dy;
        dx = -(int)scrollLeft;
        dy = (int)(touchPoint.getY());
        int time = (int)((-scrollLeft/viewWidth) * scrollTime);
        scroller.startScroll((int)(viewWidth+scrollLeft),(int)touchPoint.getY(),dx,dy,time);
        invalidate();
        currentPage --;
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            float x = scroller.getCurrX();
            float y = scroller.getCurrY();
            scrollLeft = 0 - (viewWidth - x);
            if (scroller.getFinalX() == x && scroller.getFinalY() == y){
                resetView();
            }
            postInvalidate();
        }
    }

    private void resetView(){
        scrollLeft = 0;
        touchPoint.setX(-1);
        touchPoint.setY(-1);
    }

    private void startCancelAnim(){
        int dx,dy;
        dx = (int) (viewWidth-1-touchPoint.getX());
        dy = (int) (touchPoint.getY());
        scroller.startScroll((int) touchPoint.getX(), (int) touchPoint.getY(), dx, dy, scrollTime);
        invalidate();
    }
}
