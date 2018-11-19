package com.welson.reader.base;

import com.welson.reader.contract.MainContract;

public interface BasePresenter<T extends BaseView> {
    void attachView(T t);
    void detachView();
}
