package com.welson.reader.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.adapter.BookDetailHotReviewRecyclerAdapter;
import com.welson.reader.adapter.BookDetailRecommendRecyclerAdapter;
import com.welson.reader.constant.Constants;
import com.welson.reader.contract.BookDetailContract;
import com.welson.reader.entity.BookDetail;
import com.welson.reader.entity.BookDetailEntity;
import com.welson.reader.entity.HotReview;
import com.welson.reader.entity.RecommendBookList;
import com.welson.reader.presenter.BookDetailPresenter;
import com.welson.reader.util.GlideUtil;
import com.welson.reader.util.TimeUtil;
import com.welson.reader.view.BookReadEntityLayout;
import com.welson.reader.view.BookRedButton;

import java.util.ArrayList;

public class BookDetailActivity extends AppCompatActivity implements BookDetailContract.View, View.OnClickListener {

    private Toolbar toolbar;
    private ScrollView scrollView;
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
    private TextView communityTitle;
    private TextView communityCount;
    private RecyclerView hotCommentRecycler;
    private RecyclerView recommendRecycler;
    private BookDetailHotReviewRecyclerAdapter hotReviewRecyclerAdapter;
    private BookDetailRecommendRecyclerAdapter recommendRecyclerAdapter;
    private ArrayList<RecommendBookList.BookList> bookLists;
    private ArrayList<HotReview.Review> reviews;
    private BookDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initToolbar();
        initView();
        initData();
        addListener();
    }

    private void initView() {
        scrollView = findViewById(R.id.scroll_view);
        scrollView.setVisibility(View.GONE);
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
        communityTitle = findViewById(R.id.book_detail_community_title);
        communityCount = findViewById(R.id.book_detail_community_count);
        hotCommentRecycler = findViewById(R.id.book_detail_hot_comment_recycler);
        recommendRecycler = findViewById(R.id.book_detail_recommend_recycler);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("书籍详情");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initData() {
        String id = getIntent().getStringExtra("bookId");
        presenter = new BookDetailPresenter();
        presenter.attachView(this);
        presenter.requestBookData(id);
        reviews = new ArrayList<>();
        bookLists = new ArrayList<>();
        hotReviewRecyclerAdapter = new BookDetailHotReviewRecyclerAdapter(this, reviews);
        recommendRecyclerAdapter = new BookDetailRecommendRecyclerAdapter(this, bookLists);
        LinearLayoutManager hotManager = new LinearLayoutManager(this);
        LinearLayoutManager recommendManager = new LinearLayoutManager(this);
        hotCommentRecycler.setLayoutManager(hotManager);
        hotCommentRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recommendRecycler.setLayoutManager(recommendManager);
        hotCommentRecycler.setAdapter(hotReviewRecyclerAdapter);
        recommendRecycler.setAdapter(recommendRecyclerAdapter);
    }

    private void addListener() {
        contentSummary.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    public void showSucceed(BookDetailEntity bookDetailEntity) {
        scrollView.setVisibility(View.VISIBLE);
        BookDetail bookDetail = bookDetailEntity.getBookDetail();
        HotReview hotReview = bookDetailEntity.getHotReview();

        RecommendBookList recommendBookList = bookDetailEntity.getRecommendBookList();
        GlideUtil.loadImage(this, Constants.IMG_BASE_URL + bookDetail.getCover(), bookImage);
        bookTitle.setText(bookDetail.getTitle());
        author.setText(getAuthorString(bookDetail.getAuthor(), bookDetail.getCat(), bookDetail.getWordCount()));
        publishTime.setText(TimeUtil.getDescriptionTimeFromDateString(bookDetail.getUpdated()));
        addCount.setContent(String.valueOf(bookDetail.getLatelyFollower()));
        savePercent.setContent(bookDetail.getRetentionRatio() + "%");
        refreshEachDay.setContent(bookDetail.getSerializeWordCount() > 0 ? String.valueOf(bookDetail.getSerializeWordCount())
                : String.valueOf(0));
        contentSummary.setText(bookDetail.getLongIntro());
        communityTitle.setText(bookDetail.getTitle() + "的社区");
        communityCount.setText("共有" + bookDetail.getPostCount() + "个帖子");

        reviews.clear();
        reviews.addAll(hotReview.getReviews());
        hotReviewRecyclerAdapter.notifyDataSetChanged();

        bookLists.clear();
        bookLists.addAll(recommendBookList.getBooklists());
        recommendRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_detail_content:
                int maxLines = contentSummary.getMaxLines();
                if (maxLines == 4) {
                    contentSummary.setMaxLines(Integer.MAX_VALUE);
                    contentSummary.setEllipsize(null);
                } else {
                    contentSummary.setMaxLines(4);
                    contentSummary.setEllipsize(TextUtils.TruncateAt.END);
                }
                break;
        }
    }

    private String getAuthorString(String author, String type, int wordCount) {
        return author + " | " + type + " | " + getWordCountString(wordCount);
    }

    private String getWordCountString(int wordCount) {
        if (wordCount < 10000) {
            return String.valueOf(wordCount);
        } else {
            return wordCount / 10000 + "万字";
        }
    }
}
