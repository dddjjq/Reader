package com.welson.reader.presenter;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.RankListContract;
import com.welson.reader.entity.RankingList;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RankListPresenter extends AbstractPresenter implements RankListContract.Presenter {

    private RankListContract.View view;

    @Override
    public void requestRankListData() {
        if (view == null) return;
        RetrofitHelper.getInstance().getRankings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankingList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(RankingList rankingList) {
                        view.showSucceed(rankingList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void attachView(BaseView baseView) {
        view = (RankListContract.View) baseView;
    }

    @Override
    public void detachView() {
        if (view != null) {
            view = null;
        }
    }
}
