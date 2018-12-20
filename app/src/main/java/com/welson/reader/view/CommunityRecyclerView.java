package com.welson.reader.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.welson.reader.adapter.CommunityRecyclerAdapter;

public class CommunityRecyclerView extends RecyclerView {

    private float downY,currY;
    private float dy;
    private LinearLayoutManager manager;
    private int firstVisibleItem;
    private int lastVisibleItem;
    private CommunityRecyclerAdapter.TopViewHolder topViewHolder;
    private CommunityRecyclerAdapter.BottomViewHolder bottomViewHolder;

    public CommunityRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CommunityRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        manager = (LinearLayoutManager)getLayoutManager();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                currY = e.getY();
                dy = currY - downY;
                firstVisibleItem = manager.findFirstVisibleItemPosition();
                lastVisibleItem = manager.findLastVisibleItemPosition();
                if (firstVisibleItem == 0 && currY > downY){ //当前是第一个而且在下拉
                    topViewHolder = (CommunityRecyclerAdapter.TopViewHolder)
                            getChildViewHolder(getChildAt(0));
                    topViewHolder.setTopHeight(dy);
                }
                if ((lastVisibleItem == getAdapter().getItemCount()-1) && currY < downY){
                    bottomViewHolder = (CommunityRecyclerAdapter.BottomViewHolder)
                            getChildViewHolder(getChildAt(getChildCount()-1));
                    bottomViewHolder.setTopHeight(-dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("dingyl","ACTION_UP");
                if(topViewHolder != null && firstVisibleItem == 0){
                    topViewHolder.releaseArrow(dy);
                }
                Log.d("dingyl","lastVisibleItem : " + lastVisibleItem);
                Log.d("dingyl","getAdapter().getItemCount() : " + getAdapter().getItemCount());
                if (bottomViewHolder != null){
                    Log.d("dingyl","releaseLoad");
                    bottomViewHolder.releaseLoad(-dy);
                }
                break;
        }
        return super.onTouchEvent(e);
    }
}
