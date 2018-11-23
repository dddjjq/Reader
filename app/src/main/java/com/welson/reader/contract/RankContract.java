package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.Rankings;

import java.util.ArrayList;

public class RankContract {

    public interface Presenter extends BasePresenter{
        void requestRankData(String id);
    }

    public interface View extends BaseView{
        void showSucceed(ArrayList<Rankings.Book> books);
    }
}
