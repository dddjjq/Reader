package com.welson.reader.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.welson.reader.R;
import com.welson.reader.adapter.RankDetailRecyclerAdapter;
import com.welson.reader.contract.RankDetailContract;
import com.welson.reader.entity.Rankings;
import com.welson.reader.presenter.RankPresenter;

import java.util.ArrayList;

public class RankDetailFragment extends BaseFragment implements RankDetailContract.View{

    private RecyclerView rankRecycler;
    private RankPresenter presenter;
    private RankDetailRecyclerAdapter adapter;
    private ArrayList<Rankings.Book> books;

    @Override
    public int setLayout() {
        return R.layout.fragment_rank;
    }

    @Override
    public void initView(View view) {
        rankRecycler = view.findViewById(R.id.rank_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rankRecycler.setLayoutManager(manager);
        presenter = new RankPresenter();
        presenter.attachView(this);
    }

    @Override
    public void initData() {
        books = new ArrayList<>();
        adapter = new RankDetailRecyclerAdapter(getContext(),books);
        rankRecycler.setAdapter(adapter);
        String id = getArguments().getString("id");
        presenter.requestRankData(id);
    }

    @Override
    public void addListener() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    @Override
    public void showSucceed(Rankings rankings) {
        books.clear();
        books.addAll(rankings.getRanking().getBooks());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSucceed() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showComplete() {

    }
}
