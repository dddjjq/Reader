package com.welson.reader.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.adapter.CollapseRecyclerAdapter;
import com.welson.reader.entity.RankingList;

import java.util.ArrayList;

public class CollapseLayout extends LinearLayout {

    private TextView rankCollapseText;
    private ImageView rankCollapseIndicator;
    private RecyclerView rankCollapseRecycler;
    private ArrayList<RankingList.Rank> ranks;
    private CollapseRecyclerAdapter adapter;
    private boolean isListVisible = false;

    public CollapseLayout(Context context) {
        super(context);
        init(context);
    }

    public CollapseLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        addListener();
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.rank_collapse_layout,this,true);
        rankCollapseText = findViewById(R.id.rank_collapse_text);
        rankCollapseIndicator = findViewById(R.id.rank_collapse_indicator);
        rankCollapseRecycler = findViewById(R.id.rank_collapse_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rankCollapseRecycler.setLayoutManager(manager);
    }

    private void addListener(){
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isListVisible){
                    isListVisible = true;
                    rankCollapseRecycler.setVisibility(VISIBLE);
                    rankCollapseIndicator.setImageResource(R.drawable.rank_collapse_selected);
                    rankCollapseText.setTextColor(Color.parseColor("#FF9979"));
                }else {
                    isListVisible = false;
                    rankCollapseRecycler.setVisibility(GONE);
                    rankCollapseIndicator.setImageResource(R.drawable.rank_collapse_not_select);
                    rankCollapseText.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
    }

    public void setRanks(ArrayList<RankingList.Rank> ranks){
        adapter = new CollapseRecyclerAdapter(getContext(),ranks);
        rankCollapseRecycler.setAdapter(adapter);
    }

}
