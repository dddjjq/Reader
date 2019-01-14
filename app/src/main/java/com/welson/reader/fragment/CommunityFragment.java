package com.welson.reader.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.welson.reader.R;
import com.welson.reader.activity.BookReviewActivity;
import com.welson.reader.activity.CommunityDetailActivity;
import com.welson.reader.activity.MainActivity;

public class CommunityFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout discussItem;
    private RelativeLayout commentItem;
    private RelativeLayout helperItem;
    private RelativeLayout girlItem;
    private RelativeLayout originalItem;
    private static final int REQUEST_CODE = 1;

    @Override
    public int setLayout() {
        return R.layout.fragment_community;
    }

    @Override
    public void initView(View view) {
        discussItem = view.findViewById(R.id.community_discuss_item);
        commentItem = view.findViewById(R.id.community_comment_item);
        helperItem = view.findViewById(R.id.community_helper_item);
        originalItem = view.findViewById(R.id.community_original_item);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        discussItem.setOnClickListener(this);
        commentItem.setOnClickListener(this);
        helperItem.setOnClickListener(this);
        originalItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.community_discuss_item:
                Intent intent = new Intent(getContext(), CommunityDetailActivity.class);
                intent.putExtra("block", "ramble");
                startActivityForResult(intent, REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.window_enter_anim, 0);
                break;
            case R.id.community_comment_item:
                Intent intent2 = new Intent(getContext(), BookReviewActivity.class);
                startActivity(intent2);
                break;
            case R.id.community_helper_item:

                break;
            case R.id.community_original_item:
                Intent intent4 = new Intent(getContext(), CommunityDetailActivity.class);
                intent4.putExtra("block", "original");
                startActivityForResult(intent4, REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.window_enter_anim, 0);
                break;
        }
    }

    /**
     * 这里的onActivityResult只能接收从fragment直接start的activity
     *
     * @param requestCode 1
     * @param resultCode  RESULT_OK
     * @param data        intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ((MainActivity) getActivity()).setCurrentPage(1);
        }
    }
}
