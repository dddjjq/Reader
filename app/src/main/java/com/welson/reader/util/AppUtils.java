package com.welson.reader.util;

import android.content.Context;

public class AppUtils {

    private static Context context;

    public static void init(Context mContext){
        context = mContext;
    }
    public static Context getApplicationContext(){
        return context;
    }
}
