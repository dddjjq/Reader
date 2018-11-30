package com.welson.reader.manager;

import android.util.Log;

import com.welson.reader.util.ACache;
import com.welson.reader.util.FileUtils;

import java.io.File;

public class CacheManager {

    private static CacheManager instance;

    private CacheManager(){
    }

    public static CacheManager getInstance() {
        if (instance == null){
            synchronized (CacheManager.class){
                instance = new CacheManager();
            }
        }
        return instance;
    }

    public void saveChapterFile(String bookId,int chapter,String chapterContent){
        File file = FileUtils.getChapterFile(bookId,chapter);
        FileUtils.writeFile(file,chapterContent,false);
    }
}
