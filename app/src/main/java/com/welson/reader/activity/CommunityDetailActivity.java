package com.welson.reader.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.welson.reader.R;
import com.welson.reader.adapter.CommunityRecyclerAdapter;
import com.welson.reader.contract.CommunityDetailContract;
import com.welson.reader.entity.DiscussionList;
import com.welson.reader.presenter.CommunityDetailPresenter;

import java.util.ArrayList;

public class CommunityDetailActivity extends AppCompatActivity implements CommunityDetailContract.View{

    private CommunityDetailPresenter presenter;
    private ArrayList<DiscussionList.Post> posts;
    private RecyclerView recyclerView;
    private CommunityRecyclerAdapter adapter;
    private Toolbar toolbar;
    private String block;
    private String[] sorts = {"updated","created","comment-count"};
    private String type = "all";
    private int start = 0;
    private int limit = 20;
    private String distillate = "false";

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
        toolbar = findViewById(R.id.toolbar);
    }

    private void initData(){
        posts = new ArrayList<>();
        block = getIntent().getStringExtra("block");
        if (block.equals("ramble")){
            toolbar.setTitle("综合讨论区");
        }else {
            toolbar.setTitle("原创区");
        }
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.community_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.window_exit_anim);
    }
}
