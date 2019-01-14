package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.BookReviewList;

public class BookReviewContract {

    public interface Presenter extends BasePresenter {
        void requestData(String duration, String sort, String type, int start, int limit, String distillate);
    }

    public interface View extends BaseView {
        void showSucceed(BookReviewList bookReviewList);
    }
}
