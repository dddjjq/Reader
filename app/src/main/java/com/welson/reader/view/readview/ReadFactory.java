package com.welson.reader.view.readview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.welson.reader.R;

public class ReadFactory extends PageFactory{

    private Context context;

    public ReadFactory(Context context){
        this.context = context;
    }

    @Override
    public void drawPreviousBitmap(Bitmap bitmap) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);
        paint.setColor(Color.RED);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText("11111111",300,300,paint);
    }

    @Override
    public void drawCurrentBitmap(Bitmap bitmap) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);
        paint.setColor(Color.RED);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText("22222222",300,300,paint);
    }

    @Override
    public void drawNextBitmap(Bitmap bitmap) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(50);
        paint.setColor(Color.RED);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText("33333333",300,300,paint);
    }


    /*private String loadPrePage(){

    }

    private String loadCurrentPage(){

    }*/
}
