package com.welson.reader.activity;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.adapter.CommunityRecyclerAdapter;
import com.welson.reader.base.OnRefreshListener;
import com.welson.reader.constant.Constants;
import com.welson.reader.contract.CommunityDetailContract;
import com.welson.reader.entity.DiscussionList;
import com.welson.reader.presenter.CommunityDetailPresenter;
import com.welson.reader.util.SharedPreferenceUtil;
import com.welson.reader.view.CommunityPopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommunityDetailActivity extends AppCompatActivity implements CommunityDetailContract.View
        , View.OnClickListener, CommunityPopupWindow.OnPopWindowItemClick, OnRefreshListener {

    private CommunityDetailPresenter presenter;
    private ArrayList<DiscussionList.Post> posts;
    private RecyclerView recyclerView;
    private CommunityRecyclerAdapter adapter;
    private Toolbar toolbar;
    private LinearLayout topLeftLayout;
    private LinearLayout topRightLayout;
    private TextView topLeftText;
    private ImageView topLeftImage;
    private TextView topRightText;
    private ImageView topRightImage;
    private CommunityPopupWindow communityPopupWindow;
    private String block;
    private String sort = "updated";//"updated","created","comment-count"
    private String[] sorts = {"updated", "created", "comment-count"};
    private String type = "all";
    private int start = 0;
    private int limit = 20;
    private String distillate = "";
    private String[] distillates = {"", "true"};
    private List<String> itemsLeft;
    private List<String> itemsRight;
    public int leftItem, rightItem;
    private boolean isDataClear = false;
    private CommunityRecyclerAdapter.TopViewHolder topViewHolder;
    private CommunityRecyclerAdapter.BottomViewHolder bottomViewHolder;
    private LinearLayoutManager linearLayoutManager;
    private int firstVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        initView();
        initData();
        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        SharedPreferenceUtil.removeKey(this, Constants.SP_COMM_LEFT);
        SharedPreferenceUtil.removeKey(this, Constants.SP_COMM_RIGHT);
    }

    private void initView() {
        recyclerView = findViewById(R.id.community_recycler);
        toolbar = findViewById(R.id.toolbar);
        topLeftLayout = findViewById(R.id.community_top_left);
        topRightLayout = findViewById(R.id.community_top_right);
        topLeftText = findViewById(R.id.community_top_left_text);
        topLeftImage = findViewById(R.id.community_top_left_image);
        topRightText = findViewById(R.id.community_top_right_text);
        topRightImage = findViewById(R.id.community_top_right_image);
    }

    private void initData() {
        posts = new ArrayList<>();
        block = getIntent().getStringExtra("block");
        if (block.equals("ramble")) {
            toolbar.setTitle("综合讨论区");
        } else {
            toolbar.setTitle("原创区");
        }
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new CommunityDetailPresenter();
        presenter.attachView(this);
        presenter.requestDiscuss(block, "all", sort, type, start, limit, distillate);
        adapter = new CommunityRecyclerAdapter(posts, this, recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnRefreshListener(this);
        itemsLeft = Arrays.asList(getResources().getStringArray(R.array.str_arr_community_left));
        itemsRight = Arrays.asList(getResources().getStringArray(R.array.str_arr_community_right));
        refreshUI();
    }

    private void addListener() {
        topLeftLayout.setOnClickListener(this);
        topRightLayout.setOnClickListener(this);
    }

    @Override
    public void showSucceed(DiscussionList discussionList) {
        abortAnimation();
        if (isDataClear) {
            posts.clear();
        }
        posts.addAll(discussionList.getPosts());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        abortAnimation();
    }

    private void abortAnimation() {
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        if (isDataClear && firstVisibleItem == 0) {
            topViewHolder = (CommunityRecyclerAdapter.TopViewHolder)
                    recyclerView.getChildViewHolder(recyclerView.getChildAt(0));
            topViewHolder.abortAnimation();
        } else if (!isDataClear && firstVisibleItem == adapter.getItemCount() - 1) {
            bottomViewHolder = (CommunityRecyclerAdapter.BottomViewHolder) recyclerView.getChildViewHolder
                    (recyclerView.getChildAt(recyclerView.getChildCount() - 1));
            bottomViewHolder.abortAnimation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.community_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        setResult(RESULT_OK);
        super.finish();
        overridePendingTransition(0, R.anim.window_exit_anim);
    }

    public void refreshUI() {
        leftItem = SharedPreferenceUtil.getInt(this, Constants.SP_COMM_LEFT, 0);
        rightItem = SharedPreferenceUtil.getInt(this, Constants.SP_COMM_RIGHT, 0);
        topLeftText.setText(itemsLeft.get(leftItem));
        topRightText.setText(itemsRight.get(rightItem));
        topLeftImage.setImageResource(R.drawable.community_top_indicator_down);
        topRightImage.setImageResource(R.drawable.community_top_indicator_down);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.community_top_left:
                communityPopupWindow = new CommunityPopupWindow(this, itemsLeft, true);
                communityPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
                communityPopupWindow.setOnPopWindowItemClick(CommunityDetailActivity.this);
                topLeftImage.setImageResource(R.drawable.community_top_indicator_up);
                break;
            case R.id.community_top_right:
                communityPopupWindow = new CommunityPopupWindow(this, itemsRight, false);
                communityPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
                topRightImage.setImageResource(R.drawable.community_top_indicator_up);
                communityPopupWindow.setOnPopWindowItemClick(CommunityDetailActivity.this);
                break;
        }
    }

    private void requestData(boolean isLeft, int item) {
        isDataClear = true;
        start = 0;
        if (isLeft) {
            distillate = distillates[item];
        } else {
            sort = sorts[item];
        }
        presenter.requestDiscuss(block, "all", sort, type, start, limit, distillate);
    }


    @Override
    public void onItemClick(boolean isLeft, int item) {
        requestData(isLeft, item);
    }

    @Override
    public void onRefresh() {
        isDataClear = true;
        start = 0;
        presenter.requestDiscuss(block, "all", sort, type, start, limit, distillate);
    }

    @Override
    public void onLoadMore() {
        isDataClear = false;
        start += 20;
        presenter.requestDiscuss(block, "all", sort, type, start, limit, distillate);
    }
}
