package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.BookEntity;
import com.welson.reader.entity.Recommend;

import java.util.ArrayList;

public class MainContract {

    public interface Presenter extends BasePresenter {
        void requestRecommendData(String gender);
    }

    public interface View extends BaseView {
        void showSucceed(ArrayList<BookEntity> entities, boolean isDownload);
    }
}
