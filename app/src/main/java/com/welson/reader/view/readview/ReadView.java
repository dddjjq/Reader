package com.welson.reader.view.readview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        previousBitmap = Bitmap.createBitmap(viewWidth,viewHeight, Bitmap.Config.RGB_565);
        currentBitmap = Bitmap.createBitmap(viewWidth,viewHeight, Bitmap.Config.RGB_565);
        nextBitmap = Bitmap.createBitmap(viewWidth,viewHeight, Bitmap.Config.RGB_565);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (factory != null){
            if(touchPoint.getX() == -1 && touchPoint.getY() == -1){
                drawCurrentPage(canvas);
                pageState = PAGE_STAY;
            }else {
                if (touchState == TOUCH_RIGHT){
                    drawCurrentPage(canvas);
                    drawNextPage(canvas);
                    drawShadow(canvas);
                }else if (touchState == TOUCH_LEFT){
                    drawPreviousPage(canvas);
                    drawCurrentPage(canvas);
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
                    }else if (downX > viewWidth/3*2){
                        touchState = TOUCH_RIGHT;
                    }else {
                        touchState = TOUCH_MIDDLE;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (touchState == TOUCH_LEFT){
                        scrollPage(x,y);
                    }else if ((touchState == TOUCH_RIGHT)){
                        scrollPage(x,y);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    autoScroll();
                    break;
            }
        }
        return super.onTouchEvent(event);

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
    }

    private void autoScrollToPreviousPage(){
        pageState = PAGE_PRIV;
        int dx,dy;
        dx = -(int)scrollLeft;
        dy = (int)(touchPoint.getY());
        int time = (int)((-scrollLeft/viewWidth) * scrollTime);
        scroller.startScroll((int)(viewWidth+scrollLeft),(int)touchPoint.getY(),dx,dy,time);
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
        touchPoint.setX(0);
        touchPoint.setY(0);
    }
}
