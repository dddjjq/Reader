package com.welson.reader.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    private static final String SHARE_NAME = "reader_share";

    public synchronized static int getInt(Context context,String key,int defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,defValue);
    }

    public synchronized static void putInt(Context context,String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public synchronized static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,defValue);
    }

    public synchronized static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public synchronized static String getString(Context context,String key,String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defValue);
    }

    public synchronized static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public synchronized static long getString(Context context,String key,long defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key,defValue);
    }

    public synchronized static void putLong(Context context,String key,long value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }
}
