package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;

public class MainContract {

    public interface Presenter extends BasePresenter{
        void requestRecommendData();
    }

    public interface View extends BaseView{

    }
}
