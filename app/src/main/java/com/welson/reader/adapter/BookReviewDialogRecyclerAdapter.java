package com.welson.reader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.reader.R;

import java.util.ArrayList;
import java.util.List;

public class BookReviewDialogRecyclerAdapter extends RecyclerView.Adapter<BookReviewDialogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<String> titles;
    private OnItemClickListener onItemClickListener;
    private int currentItem;

    public BookReviewDialogRecyclerAdapter(Context context, List<String> titles, int currentItem) {
        this.context = context;
        this.titles = titles;
        this.currentItem = currentItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_community_top_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int position = i;
        viewHolder.item.setText(titles.get(i));
        if (i == currentItem) {
            viewHolder.item.setTextColor(context.getResources().getColor(R.color.colorMain));
        } else {
            viewHolder.item.setTextColor(Color.parseColor("#A3A3A3"));
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO for item click
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout layout;
        TextView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.pop_community_item_layout);
            item = itemView.findViewById(R.id.pop_community_item_text);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
