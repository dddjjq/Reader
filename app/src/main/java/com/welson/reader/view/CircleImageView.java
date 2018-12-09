package com.welson.reader.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewConfiguration;

public class CircleImageView extends AppCompatImageView {

    private Paint paint;
    private Matrix matrix;
    private int radius;

    public CircleImageView(Context context) {
        super(context);
        init(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        radius = Math.min(width,height)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null){
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            float scale = getWidth()/Math.min(bitmap.getWidth(),bitmap.getHeight());
            matrix.setScale(scale,scale);
            shader.setLocalMatrix(matrix);
            paint.setShader(shader);
            canvas.drawCircle(radius,radius,radius,paint);
        }else {
            super.onDraw(canvas);
        }
    }

    private void init(Context context){
        paint = new Paint();
        paint.setAntiAlias(true);
        matrix = new Matrix();
    }
}
