package com.welson.reader.manager;

import android.content.Context;

import com.welson.reader.util.SharedPreferenceUtil;

public class SettingManager {

    private static SettingManager instance;

    private SettingManager(){

    }

    public static SettingManager getInstance() {
        if (instance == null){
            synchronized (SettingManager.class){
                instance = new SettingManager();
            }
        }
        return instance;
    }

    public void saveReadProgress(Context context,String bookId, int currentChapter, int begin){
        SharedPreferenceUtil.putInt(context,getChapterKey(bookId),currentChapter);
        SharedPreferenceUtil.putInt(context,getBeginKey(bookId),begin);
    }

    public int getReadCurrentChapter(Context context,String bookId){
        return SharedPreferenceUtil.getInt(context,getChapterKey(bookId),1);
    }

    public int getReadCurrentBegin(Context context,String bookId){
        return SharedPreferenceUtil.getInt(context,getBeginKey(bookId),0);
    }

    private String getChapterKey(String bookId){
        return bookId + "--chapter";
    }

    private String getBeginKey(String bookId){
        return bookId + "--begin";
    }
}
