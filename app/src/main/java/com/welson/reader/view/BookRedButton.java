package com.welson.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;

import com.welson.reader.R;

public class BookRedButton extends AppCompatButton {

    private int image;
    private String text;
    private int background;
    private Paint paint;
    private int defaultHeight;
    private Bitmap bitmap;
    private int bitmapSize;
    
    public BookRedButton(Context context) {
        super(context);
    }

    public BookRedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs){
        defaultHeight = 200;
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.BookRedButton);
        image = ta.getResourceId(R.styleable.BookRedButton_btnIcon,R.drawable.bookshelf_image_default);
        text = ta.getString(R.styleable.BookRedButton_btnText);
        background = ta.getColor(R.styleable.BookRedButton_backColor,getResources().getColor(R.color.colorMain));
        ta.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setBackground(new ColorDrawable(background));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("dingyl","onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapSize = getMeasuredHeight()/2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap temp = BitmapFactory.decodeResource(getResources(),image,options);
        options.inSampleSize = computeSampleSize(temp);
        Log.d("dingyl","inSampleSize : " + computeSampleSize(temp));
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(),image,options);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("dingyl","onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("dingyl","onDraw");
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,paint);
    }

    private int computeSampleSize(Bitmap temp){
        int bitmapWidth = temp.getWidth();
        int bitmapHeight = temp.getHeight();
        return Math.max(bitmapWidth/bitmapSize,bitmapHeight/bitmapSize);
    }
}
