package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.BookDetail;
import com.welson.reader.entity.BookDetailEntity;

public class BookDetailContract {
    public interface Presenter extends BasePresenter {
        void requestBookData(String id);
    }

    public interface View extends BaseView {
        void showSucceed(BookDetailEntity bookDetailEntity);
    }
}
