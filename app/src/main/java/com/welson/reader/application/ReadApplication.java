package com.welson.reader.application;

import android.app.Application;
import android.content.Context;

import com.welson.reader.manager.CollectManager;
import com.welson.reader.util.AppUtils;

public class ReadApplication extends Application {

    public static CollectManager collectManager;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        AppUtils.init(this);
        collectManager = new CollectManager(this);
    }

}
