package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.BookDetail;

public class BookDetailContract {
    public interface Presenter extends BasePresenter{
        void requestBookData(String id);
    }

    public interface View extends BaseView{
        void showSucceed(BookDetail bookDetail);
    }
}
