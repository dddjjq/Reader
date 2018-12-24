package com.welson.reader.presenter;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.BookDetailContract;
import com.welson.reader.entity.BookDetail;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookDetailPresenter extends AbstractPresenter implements BookDetailContract.Presenter{

    private BookDetailContract.View view;

    @Override
    public void requestBookData(String id) {
        RetrofitHelper.getInstance().getBookDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BookDetail bookDetail) {
                        view.showSucceed(bookDetail);
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
        view = (BookDetailContract.View) baseView;
    }

    @Override
    public void detachView() {
        if(view != null){
            view = null;
        }
    }
}
