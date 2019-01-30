package com.welson.reader.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.welson.reader.R;
import com.welson.reader.activity.BookReviewActivity;
import com.welson.reader.adapter.BookReviewDialogRecyclerAdapter;
import com.welson.reader.constant.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookReviewDialogFragment extends DialogFragment implements BookReviewDialogRecyclerAdapter.OnItemClickListener {

    private List<String> titles;
    private RecyclerView recyclerView;
    private BookReviewDialogRecyclerAdapter adapter;
    private OnDataChangeListener onDataChangeListener;
    private int titleId;
    private BookReviewActivity activity;
    private String currentString;
    private int currentItem; // 回传
    private int item;
    private boolean isItemClicked = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme);
        activity = (BookReviewActivity) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            window.setGravity(Gravity.TOP);
        }
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleId = getArguments().getInt("titleId");
        item = getArguments().getInt("currentItem");
        titles = getTitles(titleId);
        initView(view);
    }

    @SuppressWarnings("all")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.review_dialog_top_recycler);
        adapter = new BookReviewDialogRecyclerAdapter(getContext(), titles, item);
        adapter.setOnItemClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (!isItemClicked) {
            return;
        }
        switch (titleId) {
            case 0:
                activity.setLeftTitle(currentString, currentItem);
                break;
            case 1:
                activity.setMiddleTitle(currentString, currentItem);
                break;
            case 2:
                activity.setRightTitle(currentString, currentItem);
                break;
        }
        activity.notifyChange();
    }

    private List<String> getTitles(int id) {
        List<String> result = new ArrayList<>();
        switch (id) {
            case 0:
                result.clear();
                result.addAll(Arrays.asList(getResources().getStringArray(R.array.str_arr_comment_top_left)));
                break;
            case 1:
                result.clear();
                result.addAll(Arrays.asList(getResources().getStringArray(R.array.str_arr_comment_top_middle)));
                break;
            case 2:
                result.clear();
                result.addAll(Arrays.asList(getResources().getStringArray(R.array.str_arr_comment_top_right)));
                break;
        }
        return result;
    }

    @Override
    public void onItemClick(int item) {
        switch (titleId) {
            case 0:
                if (item == 0) {
                    onDataChangeListener.setDistillate("");
                } else {
                    onDataChangeListener.setDistillate("true");
                }
                break;
            case 1:
                onDataChangeListener.setType(getTypeString(item));
                break;
            case 2:
                onDataChangeListener.setSort(getSortString(item));
                break;
        }
        currentString = titles.get(item);
        currentItem = item;
        isItemClicked = true;
        getDialog().dismiss();
    }

    private String getTypeString(int item) {
        return Constants.bookTypes.get(item);
    }

    private String getSortString(int item) {
        String result = "";
        switch (item) {
            case 0:
                result = "updated";
                break;
            case 1:
                result = "created";
                break;
            case 2:
                result = "comment-count";
                break;
            case 3:
                result = "helpful";
                break;
        }
        return result;
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener {
        void setDuration(String s);

        void setSort(String s);

        void setType(String type);

        void setStart(int start);

        void setLimit(int limit);

        void setDistillate(String distillate);
    }
}
