package com.welson.reader.presenter;

import android.util.Log;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.MainContract;
import com.welson.reader.entity.Recommend;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookShelfPresenter extends AbstractPresenter implements MainContract.Presenter{

    private MainContract.View view;

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
        RetrofitHelper.getInstance().getRecommend(gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommend>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Recommend recommend) {
                        if (recommend.isOk()){
                            view.showSucceed(recommend);
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

                    }
                });
    }
}
