package com.welson.reader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.welson.reader.R;
import com.welson.reader.adapter.CommunityRecyclerAdapter;
import com.welson.reader.contract.CommunityDetailContract;
import com.welson.reader.entity.DiscussionList;
import com.welson.reader.presenter.CommunityDetailPresenter;

import java.util.ArrayList;

public class CommunityDetailActivity extends AppCompatActivity implements CommunityDetailContract.View{

    private CommunityDetailPresenter presenter;
    private String block;
    private String[] sorts = {"updated","created","comment-count"};
    private String type = "all";
    private int start = 0;
    private int limit = 20;
    private String distillate = "false";
    private ArrayList<DiscussionList.Post> posts;
    private RecyclerView recyclerView;
    private CommunityRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initView(){
        recyclerView = findViewById(R.id.community_recycler);
    }

    private void initData(){
        posts = new ArrayList<>();
        block = getIntent().getStringExtra("block");
        presenter = new CommunityDetailPresenter();
        presenter.attachView(this);
        presenter.requestDiscuss(block,"all",sorts[0],type,start,limit,distillate);
        adapter = new CommunityRecyclerAdapter(posts,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showSucceed(DiscussionList discussionList) {
        posts.clear();
        posts.addAll(discussionList.getPosts());
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
