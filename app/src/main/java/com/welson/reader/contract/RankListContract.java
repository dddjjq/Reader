package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.RankingList;
import com.welson.reader.entity.Rankings;

import java.util.ArrayList;

public class RankListContract {
    public interface Presenter extends BasePresenter {
        void requestRankListData();
    }

    public interface View extends BaseView {
        void showSucceed(RankingList rankingList);
    }
}
