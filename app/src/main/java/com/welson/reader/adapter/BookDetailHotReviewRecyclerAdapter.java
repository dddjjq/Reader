package com.welson.reader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.HotReview;
import com.welson.reader.util.GlideUtil;
import com.welson.reader.view.CircleImageView;

import java.util.ArrayList;

public class BookDetailHotReviewRecyclerAdapter extends RecyclerView.Adapter<BookDetailHotReviewRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HotReview.Review> reviews;

    public BookDetailHotReviewRecyclerAdapter(Context context, ArrayList<HotReview.Review> reviews){
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_detail_hot_review_item
                ,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL+reviews.get(i).getAuthor().getAvatar()
                ,viewHolder.image);
        viewHolder.author.setText(reviews.get(i).getAuthor().getNickname());
        viewHolder.title.setText(reviews.get(i).getTitle());
        viewHolder.content.setText(reviews.get(i).getContent());
        viewHolder.zanCount.setText(String.valueOf(reviews.get(i).getHelpful().getYes()));
        viewHolder.ratingBar.setRating(reviews.get(i).getRating());
    }

    private String getZanCount(int totalCount){
        int result = totalCount > 0 ? totalCount : 0;
        return String.valueOf(result);
    }
    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView author;
        TextView title;
        RatingBar ratingBar;
        TextView content;
        TextView zanCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.detail_hot_review_image);
            author = itemView.findViewById(R.id.detail_hot_review_author);
            title = itemView.findViewById(R.id.detail_hot_review_title);
            ratingBar = itemView.findViewById(R.id.detail_hot_review_rating);
            content = itemView.findViewById(R.id.detail_hot_review_content);
            zanCount = itemView.findViewById(R.id.detail_hot_review_zan_count);
        }
    }
}
