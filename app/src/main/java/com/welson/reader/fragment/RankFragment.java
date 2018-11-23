package com.welson.reader.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.welson.reader.R;

public class RankFragment extends BaseFragment {

    private RecyclerView rankRecycler;

    @Override
    public int setLayout() {
        return R.layout.fragment_rank;
    }

    @Override
    public void initView(View view) {
        rankRecycler = view.findViewById(R.id.rank_recycler);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }
}
