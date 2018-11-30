package com.welson.reader.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.activity.ReadActivity;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.BookEntity;
import com.welson.reader.entity.Recommend;
import com.welson.reader.util.GlideUtil;

import java.util.ArrayList;

public class BookshelfRecyclerAdapter extends RecyclerView.Adapter<BookshelfRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BookEntity> books;

    public BookshelfRecyclerAdapter(Context context, ArrayList<BookEntity> books){
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookshelf_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int position = i;
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL + books.get(i).getCover(),viewHolder.bookshelfImage);
        viewHolder.bookshelfTitle.setText(books.get(i).getTitle());
        viewHolder.bookshelfChapter.setText(books.get(i).getLastChapter());
        viewHolder.bookshelfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadActivity.class);
                intent.putExtra("bookId",books.get(position).get_id());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.window_enter_anim,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout bookshelfLayout;
        private ImageView bookshelfImage;
        private TextView bookshelfTitle;
        private TextView bookshelfChapter;
        private CheckBox bookshelfCheck;
        private ImageView bookshelfIndicator;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookshelfLayout = itemView.findViewById(R.id.bookshelf_item_layout);
            bookshelfImage = itemView.findViewById(R.id.bookshelf_image);
            bookshelfTitle = itemView.findViewById(R.id.bookshelf_title);
            bookshelfChapter = itemView.findViewById(R.id.bookshelf_chapter);
            bookshelfCheck = itemView.findViewById(R.id.bookshelf_checkbox);
            bookshelfIndicator = itemView.findViewById(R.id.bookshelf_indicator);
        }
    }
}
