package com.welson.reader.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.welson.reader.R;
import com.welson.reader.activity.MainActivity;
import com.welson.reader.adapter.BookshelfRecyclerAdapter;
import com.welson.reader.application.ReadApplication;
import com.welson.reader.contract.MainContract;
import com.welson.reader.entity.BookEntity;
import com.welson.reader.entity.Recommend;
import com.welson.reader.eventbus.DownloadEvent;
import com.welson.reader.manager.CollectManager;
import com.welson.reader.presenter.BookShelfPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.annotations.NonNull;

public class BookShelfFragment extends BaseFragment implements MainContract.View,View.OnClickListener{

    private static final String TAG = "BookShelfFragment";
    private BookShelfPresenter presenter;
    private MainActivity activity;
    private Button addButton;
    private RecyclerView bookshelfRecycler;
    private BookshelfRecyclerAdapter adapter;
    private LinearLayout bookshelfEmptyView;
    private static final String MALE = "male";
    private static final String FEMALE = "female";
    private ArrayList<BookEntity> books;
    private EventBus eventBus;

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
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        presenter = new BookShelfPresenter();
        presenter.attachView(this);
        activity = (MainActivity)getActivity();
        books = new ArrayList<>();
        adapter = new BookshelfRecyclerAdapter(getContext(),books);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        bookshelfRecycler.setLayoutManager(manager);
        bookshelfRecycler.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext()
                ,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.bookshelf_item_divider));
        bookshelfRecycler.addItemDecoration(dividerItemDecoration);
        if (CollectManager.getRecommendCollectList() != null &&
                CollectManager.getRecommendCollectList().size() != 0){
            books.clear();
            books.addAll(CollectManager.getRecommendCollectList());
            showSucceed(CollectManager.getRecommendCollectList(),false);
            Log.d(TAG,"getBooks().size() != 0");
        }
        invalidate();
    }

    @Override
    public void addListener() {
        addButton.setOnClickListener(this);
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
    public void showSucceed(ArrayList<BookEntity> entities,boolean isDownload) {
        books.clear();
        books.addAll(entities);
        adapter.notifyDataSetChanged();
        invalidate();
        //if (isDownload){
            DownloadEvent event = new DownloadEvent();
            Integer i[] = {0,1,2,3};
            for (BookEntity entity : entities){
                event.setBookId(entity.get_id());
                event.setChapters(Arrays.asList(i));
                eventBus.post(event);
            }
        //}
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_button:
                activity.scrollToPage(2);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DownloadEvent event){

    }
}
