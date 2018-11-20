package com.welson.reader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.Recommend;
import com.welson.reader.util.GlideUtil;

import java.util.ArrayList;

public class BookshelfRecyclerAdapter extends RecyclerView.Adapter<BookshelfRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Recommend.Book> books;

    public BookshelfRecyclerAdapter(Context context, ArrayList<Recommend.Book> books){
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
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL + books.get(i).getCover(),viewHolder.bookshelfImage);
        viewHolder.bookshelfTitle.setText(books.get(i).getTitle());
        viewHolder.bookshelfChapter.setText(books.get(i).getLastChapter());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView bookshelfImage;
        private TextView bookshelfTitle;
        private TextView bookshelfChapter;
        private CheckBox bookshelfCheck;
        private ImageView bookshelfIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookshelfImage = itemView.findViewById(R.id.bookshelf_image);
            bookshelfTitle = itemView.findViewById(R.id.bookshelf_title);
            bookshelfChapter = itemView.findViewById(R.id.bookshelf_chapter);
            bookshelfCheck = itemView.findViewById(R.id.bookshelf_checkbox);
            bookshelfIndicator = itemView.findViewById(R.id.bookshelf_indicator);
        }
    }
}
