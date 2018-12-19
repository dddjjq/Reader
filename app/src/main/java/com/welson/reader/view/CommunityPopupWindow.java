package com.welson.reader.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.welson.reader.R;
import com.welson.reader.activity.CommunityDetailActivity;
import com.welson.reader.adapter.CommunityPopRecyclerAdapter;
import com.welson.reader.constant.Constants;
import com.welson.reader.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class CommunityPopupWindow extends PopupWindow implements View.OnClickListener,CommunityPopRecyclerAdapter.OnItemClickListener{

    private Context context;
    private List<String> items;
    private View contentView;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private CommunityPopRecyclerAdapter adapter;
    private CommunityDetailActivity activity;
    private boolean isLeft;
    private int currentItem;
    private OnPopWindowItemClick onPopWindowItemClick;

    public CommunityPopupWindow(Context context, List<String> items, boolean isLeft){
        this.context = context;
        this.items = items;
        this.isLeft = isLeft;
        init(context);
        initView();
        initData();
        addListener();
    }

    private void init(Context context){
        activity = (CommunityDetailActivity)context;
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_community_top,null);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);
    }

    private void initView(){
        recyclerView = contentView.findViewById(R.id.pop_community_top_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(manager);
        imageView = contentView.findViewById(R.id.community_pop_other);
        imageView.setAlpha(0.15f);
    }

    private void initData(){
        if(isLeft){
            currentItem = activity.leftItem;
        }else {
            currentItem = activity.rightItem;
        }
        adapter = new CommunityPopRecyclerAdapter(context,items,currentItem);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void addListener(){
        imageView.setOnClickListener(this);
        setAnimationStyle(R.style.CommunityPop);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.community_pop_other:
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Log.d("dingyl","dismiss");
        activity.refreshUI();
    }

    @Override
    public void onItemClick(int item) {
        if (isLeft){
            SharedPreferenceUtil.putInt(context,Constants.SP_COMM_LEFT,item);
        }else {
            SharedPreferenceUtil.putInt(context,Constants.SP_COMM_RIGHT,item);
        }
        if(onPopWindowItemClick != null){
            onPopWindowItemClick.onItemClick(isLeft,item);
        }
        dismiss();
    }

    public interface OnPopWindowItemClick{
        void onItemClick(boolean isLeft,int item);
    }

    public void setOnPopWindowItemClick(OnPopWindowItemClick onPopWindowItemClick){
        this.onPopWindowItemClick = onPopWindowItemClick;
    }
}
