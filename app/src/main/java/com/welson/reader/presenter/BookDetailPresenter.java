package com.welson.reader.presenter;

import android.util.Log;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.BookDetailContract;
import com.welson.reader.entity.BookDetail;
import com.welson.reader.entity.BookDetailEntity;
import com.welson.reader.entity.BookEntity;
import com.welson.reader.entity.HotReview;
import com.welson.reader.entity.RecommendBookList;
import com.welson.reader.retrofit.RetrofitHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class BookDetailPresenter extends AbstractPresenter implements BookDetailContract.Presenter {

    private BookDetailContract.View view;

    @Override
    public void requestBookData(String id) {
        if (view == null) return;
        Observable.zip(RetrofitHelper.getInstance().getBookDetail(id), RetrofitHelper.getInstance().getHotReview(id),
                RetrofitHelper.getInstance().getRecommendBookList(id, String.valueOf(3))
                , new Function3<BookDetail, HotReview, RecommendBookList, BookDetailEntity>() {

                    @Override
                    public BookDetailEntity apply(BookDetail bookDetail, HotReview hotReview, RecommendBookList recommendBookList) throws Exception {
                        BookDetailEntity bookEntity = new BookDetailEntity();
                        bookEntity.setBookDetail(bookDetail);
                        bookEntity.setHotReview(hotReview);
                        bookEntity.setRecommendBookList(recommendBookList);
                        return bookEntity;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetailEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BookDetailEntity bookDetailEntity) {
                        view.showSucceed(bookDetailEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*RetrofitHelper.getInstance().getBookDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BookDetail bookDetail) {
                        Log.d("dingyl","onNext");
                        view.showSucceed(bookDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("dingyl","onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    @Override
    public void attachView(BaseView baseView) {
        view = (BookDetailContract.View) baseView;
    }

    @Override
    public void detachView() {
        if (view != null) {
            view = null;
        }
    }
}
