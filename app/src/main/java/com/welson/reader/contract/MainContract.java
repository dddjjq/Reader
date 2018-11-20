package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.Recommend;

public class MainContract {

    public interface Presenter extends BasePresenter{
        void requestRecommendData(String gender);
    }

    public interface View extends BaseView{
        void showSucceed(Recommend recommend);
    }
}
