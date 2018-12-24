package com.welson.reader.activity;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.contract.BookDetailContract;
import com.welson.reader.entity.BookDetail;
import com.welson.reader.presenter.BookDetailPresenter;
import com.welson.reader.view.BookReadEntityLayout;
import com.welson.reader.view.BookRedButton;

public class BookDetailActivity extends AppCompatActivity implements BookDetailContract.View{

    private Toolbar toolbar;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView author;
    private TextView publishTime;
    private BookRedButton addToShelfBtn;
    private BookRedButton readBtn;
    private BookReadEntityLayout addCount;
    private BookReadEntityLayout savePercent;
    private BookReadEntityLayout refreshEachDay;
    private TextView contentSummary;
    private RecyclerView hotCommentRecycler;
    private RecyclerView recommendRecycler;

    private BookDetailPresenter presenter;
    private String id; // TODO to add id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initToolbar();
        initView();
        initData();
    }

    private void initView(){
        bookImage = findViewById(R.id.book_detail_image);
        bookTitle = findViewById(R.id.book_detail_title);
        author = findViewById(R.id.book_detail_author);
        publishTime = findViewById(R.id.book_detail_time);
        addToShelfBtn = findViewById(R.id.book_detail_add_to_bookshelf_btn);
        readBtn = findViewById(R.id.book_detail_read_btn);
        addCount = findViewById(R.id.book_detail_add_count);
        savePercent = findViewById(R.id.book_detail_save_precent);
        refreshEachDay = findViewById(R.id.book_detail_refresh_each);
        contentSummary = findViewById(R.id.book_detail_content);
        hotCommentRecycler = findViewById(R.id.book_detail_hot_comment_recycler);
        recommendRecycler = findViewById(R.id.book_detail_recommend_recycler);
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("书籍详情");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initData(){
        presenter = new BookDetailPresenter();
        presenter.attachView(this);
        presenter.requestBookData(id);
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showSucceed(BookDetail bookDetail) {

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
