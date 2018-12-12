package com.welson.reader.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.welson.reader.R;
import com.welson.reader.adapter.CommunityPopRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommunityPopupWindow extends PopupWindow implements View.OnClickListener{

    private Context context;
    private List<String> items;
    private View contentView;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private CommunityPopRecyclerAdapter adapter;

    public CommunityPopupWindow(Context context, List<String> items){
        this.context = context;
        this.items = items;
        init(context);
        initView();
        initData();
        addListener();
    }

    private void init(Context context){
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.pop_anim_enter);
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_community_top,null);
        setContentView(contentView);
        //contentView.setAnimation(animation);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //setBackgroundDrawable(new ColorDrawable());
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
        adapter = new CommunityPopRecyclerAdapter(context,items);
        recyclerView.setAdapter(adapter);
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
}
