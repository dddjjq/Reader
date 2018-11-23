package com.welson.reader.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.welson.reader.R;
import com.welson.reader.contract.RankListContract;
import com.welson.reader.entity.RankingList;
import com.welson.reader.view.RankLayout;

public class RankActivity extends AppCompatActivity implements RankListContract.View{

    private Toolbar toolbar;
    private RecyclerView rankListRecycler;
    public RankingList rankingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initToolBar();
        initView();
        addListener();
    }

    private void initToolBar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.str_discover_rank));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initView(){
        rankListRecycler = findViewById(R.id.rank_list_recycler);
    }

    private void addListener(){

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
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void showSucceed(RankingList rankingList) {
        this.rankingList = rankingList;
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
