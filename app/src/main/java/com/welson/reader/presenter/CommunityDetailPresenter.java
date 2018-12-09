package com.welson.reader.presenter;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.CommunityDetailContract;
import com.welson.reader.entity.DiscussionList;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommunityDetailPresenter extends AbstractPresenter implements CommunityDetailContract.Presenter{

    private CommunityDetailContract.View view;

    @Override
    public void requestDiscuss(String block, String duration, String sort, String type, int start, int limit, String distillate) {
        RetrofitHelper.getInstance().getBookDiscussionList(block, duration, sort, type, start, limit, distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscussionList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(DiscussionList discussionList) {
                        view.showSucceed(discussionList);
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
        view = (CommunityDetailContract.View) baseView;
    }

    @Override
    public void detachView() {
        if (view != null){
            view = null;
        }
    }
}
