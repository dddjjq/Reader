package com.welson.reader.presenter;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.BookReviewContract;
import com.welson.reader.entity.BookReviewList;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookReviewPresenter extends AbstractPresenter implements BookReviewContract.Presenter {

    private BookReviewContract.View view;

    @Override
    public void requestData(String duration, String sort, String type, int start, int limit, String distillate) {
        if (view == null) return;
        RetrofitHelper.getInstance().getBookReviewList(duration, sort, type, start, limit, distillate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookReviewList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BookReviewList bookReviewList) {
                        view.showSucceed(bookReviewList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void attachView(BaseView baseView) {
        view = (BookReviewContract.View) baseView;
    }

    @Override
    public void detachView() {
        if (view != null) {
            view = null;
        }
    }
}
