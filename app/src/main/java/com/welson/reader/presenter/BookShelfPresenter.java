package com.welson.reader.presenter;

import android.util.Log;

import com.welson.reader.application.ReadApplication;
import com.welson.reader.base.BaseView;
import com.welson.reader.constant.Constants;
import com.welson.reader.contract.MainContract;
import com.welson.reader.entity.BookEntity;
import com.welson.reader.entity.Recommend;
import com.welson.reader.manager.CollectManager;
import com.welson.reader.retrofit.RetrofitHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookShelfPresenter extends AbstractPresenter implements MainContract.Presenter{

    private static final String TAG = "BookShelfPresenter";
    private MainContract.View view;
    private Recommend recommend;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public void attachView(BaseView baseView) {
        view = (MainContract.View)baseView;
    }

    @Override
    public void detachView() {
        if (view != null){
            view = null;
        }
    }

    @Override
    public void requestRecommendData(String gender) {
        if (view == null) return;
        RetrofitHelper.getInstance().getRecommend(gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommend>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Recommend mRecommend) {
                        recommend = mRecommend;
                        if (mRecommend.isOk()){
                            view.showSucceed(mRecommend.getBooks(),true);
                        }else {
                            view.showError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        executorService.execute(addCollectRunnable);
                        Log.d(TAG, Constants.PATH_DATA);
                    }
                });
    }

    private Runnable addCollectRunnable = new Runnable() {
        @Override
        public void run() {
            ArrayList<BookEntity> bookEntities = recommend.getBooks();
            CollectManager.addCollect("collect",bookEntities);
        }
    };

}
