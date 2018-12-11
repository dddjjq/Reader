package com.welson.reader.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.welson.reader.R;
import com.welson.reader.adapter.CommunityPopRecyclerAdapter;

import java.util.ArrayList;

public class CommunityPopupWindow extends PopupWindow {

    private Context context;
    private ArrayList<String> items;
    private View contentView;
    private RecyclerView recyclerView;
    private CommunityPopRecyclerAdapter adapter;

    public CommunityPopupWindow(Context context, ArrayList<String> items){
        this.context = context;
        this.items = items;
        init(context);
        initView();
        initData();
    }

    private void init(Context context){
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_community_top,null);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
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
    }

    private void initData(){
        adapter = new CommunityPopRecyclerAdapter(context,items);
        recyclerView.setAdapter(adapter);
    }
}
