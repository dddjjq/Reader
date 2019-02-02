package com.welson.reader.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.welson.reader.activity.BookReviewActivity;

public class ReviewRecyclerView extends RecyclerView {

    private int count = 0;

    public ReviewRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ReviewRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (canLoadMore()){
                    Activity activity = (Activity)getContext();
                    if (activity instanceof BookReviewActivity){
                        ((BookReviewActivity) activity).setStart(++count*20);
                        ((BookReviewActivity) activity).notifyChange(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean canLoadMore() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int state = getScrollState();
        if (visibleItemCount > 0 && (lastVisibleItemPosition == totalItemCount - 1)
                && state == SCROLL_STATE_IDLE) {
            return true;
        }
        return false;
    }
}
