package com.welson.reader.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.welson.reader.manager.CollectManager;
import com.welson.reader.presenter.AbstractPresenter;
import com.welson.reader.util.AppUtils;
import com.welson.reader.util.SharedPreferenceUtil;

public class ReadApplication extends Application {

    private static final String TAG = ReadApplication.class.getSimpleName();
    public static CollectManager collectManager;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        AppUtils.init(this);
        collectManager = new CollectManager(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG,"omLowMemory");
        AbstractPresenter.removeAllDisposable();
        SharedPreferenceUtil.removeKey(this,"currentItem");
    }
}
