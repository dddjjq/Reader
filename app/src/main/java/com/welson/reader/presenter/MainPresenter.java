package com.welson.reader.presenter;

import com.welson.reader.base.BaseView;
import com.welson.reader.contract.MainContract;

public class MainPresenter extends AbstractPresenter implements MainContract.Presenter{

    private MainContract.View view;

    @Override
    public void attachView(BaseView baseView) {
        view = (MainContract.View)baseView;
    }

    @Override
    public void detachView() {
        if (view != null){
            view = null;
        }
    }

    @Override
    public void requestRecommendData() {

    }
}
