package com.welson.reader.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.adapter.BookReviewRecyclerAdapter;
import com.welson.reader.contract.BookReviewContract;
import com.welson.reader.entity.BookReviewList;
import com.welson.reader.fragment.BookReviewDialogFragment;
import com.welson.reader.presenter.BookReviewPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookReviewActivity extends AppCompatActivity implements BookReviewContract.View,
        View.OnClickListener, BookReviewDialogFragment.OnDataChangeListener {

    private static final String TAG = BookReviewActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BookReviewPresenter presenter;
    private Toolbar toolbar;
    private LinearLayout reviewTopLeft, reviewTopMiddle, reviewTopRight;
    private TextView reviewTopLeftTitle, reviewTopMiddleTitle, reviewTopRightTitle;
    private ImageView reviewTopLeftImage, reviewTopMiddleImage, reviewTopRightImage;
    private BookReviewRecyclerAdapter adapter;
    private ArrayList<BookReviewList.Review> reviews;
    private String duration = "all";
    private String sort = "updated";
    private String type = "all";
    private int start = 0;
    private int limit = 20;
    private String distillate = "";
    private BookReviewDialogFragment dialogFragment;
    private FragmentManager fragmentManager;
    private int currentLeft, currentMiddle, currentRight;
    private boolean isClearData = true; //是否清除数据

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
        toolbar = findViewById(R.id.toolbar);
        reviewTopLeft = findViewById(R.id.book_review_top_left);
        reviewTopLeftImage = findViewById(R.id.book_review_top_left_image);
        reviewTopLeftTitle = findViewById(R.id.book_review_top_left_text);
        reviewTopRight = findViewById(R.id.book_review_top_right);
        reviewTopRightImage = findViewById(R.id.book_review_top_right_image);
        reviewTopRightTitle = findViewById(R.id.book_review_top_right_text);
        reviewTopMiddle = findViewById(R.id.book_review_top_middle);
        reviewTopMiddleImage = findViewById(R.id.book_review_top_middle_image);
        reviewTopMiddleTitle = findViewById(R.id.book_review_top_middle_text);
        toolbar.setTitle("书评区");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initData() {
        reviews = new ArrayList<>();
        presenter = new BookReviewPresenter();
        presenter.attachView(this);
        notifyChange(true);
        fragmentManager = getSupportFragmentManager();
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
                start = 0;
                presenter.requestData(duration, sort, type, start, limit, distillate);
                isClearData = true;
            }
        });
        reviewTopLeft.setOnClickListener(this);
        reviewTopMiddle.setOnClickListener(this);
        reviewTopRight.setOnClickListener(this);
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
        if (isClearData){
            reviews.clear();
        }
        reviews.addAll(bookReviewList.getReviews());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
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
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.window_exit_anim);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.book_review_top_left:
                dialogFragment = new BookReviewDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("titleId", 0);
                bundle.putInt("currentItem", currentLeft);
                dialogFragment.setArguments(bundle);
                break;
            case R.id.book_review_top_middle:
                dialogFragment = new BookReviewDialogFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("titleId", 1);
                bundle1.putInt("currentItem", currentMiddle);
                dialogFragment.setArguments(bundle1);
                break;
            case R.id.book_review_top_right:
                dialogFragment = new BookReviewDialogFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("titleId", 2);
                bundle2.putInt("currentItem", currentRight);
                dialogFragment.setArguments(bundle2);
                break;
        }
        transaction.add(dialogFragment, "left");
        transaction.show(dialogFragment);
        dialogFragment.setOnDataChangeListener(this);
        transaction.commit();
    }

    @Override
    public void setDuration(String s) {
        duration = s;
    }

    @Override
    public void setSort(String s) {
        sort = s;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void setDistillate(String distillate) {
        this.distillate = distillate;
    }

    public void notifyChange(boolean isClear) {
        this.isClearData = isClear;
        presenter.requestData(duration, sort, type, start, limit, distillate);
        swipeRefreshLayout.setRefreshing(true);
    }

    public void setLeftTitle(String text, int item) {
        reviewTopLeftTitle.setText(text);
        currentLeft = item;
    }

    public void setMiddleTitle(String text, int item) {
        reviewTopMiddleTitle.setText(text);
        currentMiddle = item;
    }

    public void setRightTitle(String text, int item) {
        reviewTopRightTitle.setText(text);
        currentRight = item;
    }

}