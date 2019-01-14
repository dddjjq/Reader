package com.welson.reader.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.welson.reader.R;
import com.welson.reader.adapter.BookReviewRecyclerAdapter;
import com.welson.reader.contract.BookReviewContract;
import com.welson.reader.entity.BookReviewList;
import com.welson.reader.presenter.BookReviewPresenter;

import java.util.ArrayList;

public class BookReviewActivity extends AppCompatActivity implements BookReviewContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BookReviewPresenter presenter;
    private BookReviewRecyclerAdapter adapter;
    private ArrayList<BookReviewList.Review> reviews;
    private String duration = "all";
    private String sort = "updated";
    private String type = "all";
    private int start = 0;
    private int limit = 20;
    private String distillate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment);
        initView();
        initData();
        addListener();
    }

    private void initView() {
        recyclerView = findViewById(R.id.book_review_recycler);
        swipeRefreshLayout = findViewById(R.id.comment_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
    }

    private void initData() {
        reviews = new ArrayList<>();
        presenter = new BookReviewPresenter();
        presenter.attachView(this);
        presenter.requestData(duration, sort, type, start, limit, distillate);
        adapter = new BookReviewRecyclerAdapter(reviews, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void addListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestData(duration, sort, type, start, limit, distillate);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showSucceed(BookReviewList bookReviewList) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        reviews.clear();
        reviews.addAll(bookReviewList.getReviews());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
