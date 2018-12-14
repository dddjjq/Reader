package com.welson.reader.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtil {
    public static void loadImage(Context context,String url, ImageView imageView){
        Glide.with(context).asBitmap().load(url).into(imageView);
    }
}
