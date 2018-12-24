package com.welson.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;

import com.welson.reader.R;
import com.welson.reader.util.DensityUtil;

public class BookRedButton extends AppCompatButton {

    private int image;
    private String text;
    private int background;
    private Paint paint;
    private Bitmap bitmap;
    private int bitmapSize;
    private int imageLeft; //图片左边坐标
    private int textLeft;  //文字左边坐标
    private int top;       //顶部坐标
    private int textSize = 17;

    public BookRedButton(Context context) {
        super(context);
    }

    public BookRedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.BookRedButton);
        image = ta.getResourceId(R.styleable.BookRedButton_btnIcon,R.drawable.bookshelf_image_default);
        text = ta.getString(R.styleable.BookRedButton_btnText);
        background = ta.getColor(R.styleable.BookRedButton_backColor,getResources().getColor(R.color.colorMain));
        ta.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(DensityUtil.sp2px(context,textSize));
        setBackgroundColor(background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapSize = getMeasuredHeight()*2/5;
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap temp = BitmapFactory.decodeResource(getResources(),image,options);
        options.inSampleSize = computeSampleSize(temp);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(),image,options);

        int drawWidth = (int)(bitmap.getWidth() + DensityUtil.dp2px(getContext(),5) + paint.measureText(text));
        imageLeft = (getMeasuredWidth() - drawWidth) / 2;
        textLeft = imageLeft + DensityUtil.dp2px(getContext(),5) + bitmap.getWidth();
        top = getMeasuredHeight()/2 - bitmap.getHeight()/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,imageLeft,top,paint);
        canvas.drawText(text,textLeft,getMeasuredHeight()/2+
                DensityUtil.sp2px(getContext(),textSize)/4
                +DensityUtil.sp2px(getContext(),textSize)/10,paint);
                //DensityUtil.sp2px(getContext(),textSize)/10 这里作为基线和下边缘的差距，具体多少后面再研究了
    }

    private int computeSampleSize(Bitmap temp){
        int bitmapWidth = temp.getWidth();
        int bitmapHeight = temp.getHeight();
        return Math.max(bitmapWidth/bitmapSize,bitmapHeight/bitmapSize);
    }
}
