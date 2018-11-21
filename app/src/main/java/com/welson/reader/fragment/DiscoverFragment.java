package com.welson.reader.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.welson.reader.R;
import com.welson.reader.activity.RankActivity;

public class DiscoverFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout rankItem;
    private RelativeLayout bookListItem;
    private RelativeLayout categoryItem;
    private RelativeLayout audioItem;

    @Override
    public int setLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void initView(View view) {
        rankItem = view.findViewById(R.id.discover_rank_item);
        bookListItem = view.findViewById(R.id.discover_book_list_item);
        categoryItem = view.findViewById(R.id.discover_category_item);
        audioItem = view.findViewById(R.id.discover_audio_item);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        rankItem.setOnClickListener(this);
        bookListItem.setOnClickListener(this);
        categoryItem.setOnClickListener(this);
        audioItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.discover_rank_item:
                Intent intent = new Intent(getContext(), RankActivity.class);
                startActivity(intent);
                break;
            case R.id.discover_book_list_item:
                break;
            case R.id.discover_category_item:
                break;
            case R.id.discover_audio_item:
                break;
        }
    }
}
