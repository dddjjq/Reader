package com.welson.reader.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.welson.reader.R;
import com.welson.reader.adapter.BookshelfRecyclerAdapter;
import com.welson.reader.contract.MainContract;
import com.welson.reader.entity.Recommend;
import com.welson.reader.presenter.BookShelfPresenter;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class BookShelfFragment extends BaseFragment implements MainContract.View{

    private BookShelfPresenter presenter;
    private Button addButton;
    private RecyclerView bookshelfRecycler;
    private BookshelfRecyclerAdapter adapter;
    private LinearLayout bookshelfEmptyView;
    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private ArrayList<Recommend.Book> books;

    @Override
    public int setLayout() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    public void initView(View view) {
        addButton = view.findViewById(R.id.add_button);
        bookshelfRecycler = view.findViewById(R.id.bookshelf_recycler);
        bookshelfEmptyView = view.findViewById(R.id.bookshelf_empty_view);
    }

    @Override
    public void initData() {
        presenter = new BookShelfPresenter();
        presenter.attachView(this);
        books = new ArrayList<>();
        adapter = new BookshelfRecyclerAdapter(getContext(),books);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        bookshelfRecycler.setLayoutManager(manager);
        bookshelfRecycler.setAdapter(adapter);
        bookshelfRecycler.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void firstLoadData(boolean isMale){
        if (isMale){
            presenter.requestRecommendData(MALE);
        }else {
            presenter.requestRecommendData(FEMALE);
        }
    }

    @Override
    public void showSucceed(Recommend recommend) {
        books.clear();
        books.addAll(recommend.getBooks());
        adapter.notifyDataSetChanged();
        invalidate();
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

    public void invalidate(){
        if (books.size() == 0){
            bookshelfEmptyView.setVisibility(View.VISIBLE);
        }else {
            bookshelfEmptyView.setVisibility(View.GONE);
        }
    }
}
