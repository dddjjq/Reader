package com.welson.reader.presenter;

import android.util.Log;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.RankDetailContract;
import com.welson.reader.entity.Rankings;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RankPresenter extends AbstractPresenter implements RankDetailContract.Presenter{

    private RankDetailContract.View view;

    public RankPresenter(){

    }

    @Override
    public void requestRankData(String id) {
        RetrofitHelper.getInstance().getRanking(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Rankings>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Rankings mRankings) {
                        view.showSucceed(mRankings);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.d("dingyl","onerror");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void attachView(BaseView baseView) {
        view = (RankDetailContract.View) baseView;
    }

    @Override
    public void detachView() {
        if(view != null){
            view = null;
        }
    }
}
