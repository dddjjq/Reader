package com.welson.reader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.welson.reader.R;
import com.welson.reader.contract.BookReviewContract;
import com.welson.reader.entity.BookReviewList;

public class BookReviewActivity extends AppCompatActivity implements BookReviewContract.View{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment);
        initView();
    }

    private void initView(){

    }

    @Override
    public void showSucceed(BookReviewList bookReviewList) {

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
