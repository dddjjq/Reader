package com.welson.reader.view.readview;

import android.graphics.Bitmap;

public abstract class PageFactory {

    public abstract void drawPreviousBitmap(Bitmap bitmap);

    public abstract void drawCurrentBitmap(Bitmap bitmap);

    public abstract void drawNextBitmap(Bitmap bitmap);

}
