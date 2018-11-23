package com.welson.reader.presenter;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.contract.RankContract;
import com.welson.reader.entity.Rankings;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RankPresenter extends AbstractPresenter implements RankContract.Presenter{

    private RankContract.View view;

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
                        view.showSucceed(mRankings.getRanking().getBooks());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void attachView(BaseView baseView) {
        view = (RankContract.View) baseView;
    }

    @Override
    public void detachView() {
        if(view != null){
            view = null;
        }
    }
}
