package com.welson.reader.contract;

import com.welson.reader.base.BasePresenter;
import com.welson.reader.base.BaseView;
import com.welson.reader.entity.DiscussionList;

public class CommunityDetailContract {

    public interface Presenter extends BasePresenter{
        void requestDiscuss(String block,String duration,String sort,String type,int start,int limit,String distillate);
    }

    public interface View extends BaseView{
        void showSucceed(DiscussionList discussionList);
    }
}
