package com.welson.reader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.BookReviewList;
import com.welson.reader.util.GlideUtil;
import com.welson.reader.util.TimeUtil;

import java.util.ArrayList;

public class BookReviewRecyclerAdapter extends RecyclerView.Adapter<BookReviewRecyclerAdapter.ViewHolder> {

    private ArrayList<BookReviewList.Review> reviews;
    private Context context;

    public BookReviewRecyclerAdapter(ArrayList<BookReviewList.Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_review_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL + reviews.get(i).getBook().getCover(), viewHolder.imageView);
        viewHolder.title.setText(reviews.get(i).getBook().getTitle());
        viewHolder.content.setText(reviews.get(i).getTitle());
        viewHolder.recommendCount.setText(getRecommendString(reviews.get(i).getHelpful().getYes()));
        viewHolder.time.setText(TimeUtil.getDescriptionTimeFromDateString(reviews.get(i).getCreated()));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public String getRecommendString(int count) {
        return String.valueOf(count) + " 人推荐";
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, content, recommendCount, time;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_review_item_image);
            title = itemView.findViewById(R.id.book_review_item_title);
            content = itemView.findViewById(R.id.book_review_item_content);
            recommendCount = itemView.findViewById(R.id.book_review_item_recommend_count);
            time = itemView.findViewById(R.id.book_review_item_time);
        }
    }
}
