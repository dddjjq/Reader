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
import com.welson.reader.entity.RecommendBookList;
import com.welson.reader.util.GlideUtil;

import java.util.ArrayList;


public class BookDetailRecommendRecyclerAdapter extends RecyclerView.Adapter<BookDetailRecommendRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RecommendBookList.BookList> bookLists;

    public BookDetailRecommendRecyclerAdapter(Context context, ArrayList<RecommendBookList.BookList> bookLists) {
        this.context = context;
        this.bookLists = bookLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_detail_recommend_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL + bookLists.get(i).getCover()
                , viewHolder.imageView);
        viewHolder.title.setText(bookLists.get(i).getTitle());
        viewHolder.author.setText(bookLists.get(i).getAuthor());
        viewHolder.content.setText(bookLists.get(i).getDesc());
        viewHolder.count.setText(getCountString(bookLists.get(i).getBookCount(), bookLists.get(i).getCollectorCount()));
    }

    @Override
    public int getItemCount() {
        return bookLists.size();
    }

    private String getCountString(int book, int people) {
        return "共" + String.valueOf(book) + "本书" + " | " +
                String.valueOf(people) + "人收藏";
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView author;
        TextView content;
        TextView count;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_recommend_image);
            title = itemView.findViewById(R.id.detail_recommend_title);
            author = itemView.findViewById(R.id.detail_recommend_author);
            content = itemView.findViewById(R.id.detail_recommend_content);
            count = itemView.findViewById(R.id.detail_recommend_count);
        }
    }
}
