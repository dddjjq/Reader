package com.welson.reader.manager;

import android.content.Context;

import com.welson.reader.entity.BookEntity;
import com.welson.reader.entity.Recommend;
import com.welson.reader.util.ACache;

import java.io.Serializable;
import java.util.ArrayList;

public class CollectManager {

    private static CollectManager instance;
    private static ACache aCache;

    public CollectManager(Context context){
        aCache = ACache.get(context,"collect");
    }

    /*public static CollectManager getInstance(Context context) {
        if (instance == null){
            synchronized(CollectManager.class){
                instance = new CollectManager(context);
            }
        }
        return instance;
    }*/

    public synchronized static void addCollect(String key, Serializable serializable){
        aCache.put(key,serializable);
    }

    public synchronized static ArrayList<BookEntity> getRecommendCollectList(){
        return (ArrayList<BookEntity>)(aCache.getAsObject("collect"));
    }

    public synchronized static void deleteCollect(String key){
        aCache.remove(key);
    }
}
