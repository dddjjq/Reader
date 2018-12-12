package com.welson.reader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welson.reader.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityPopRecyclerAdapter extends RecyclerView.Adapter<CommunityPopRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<String> items;

    public CommunityPopRecyclerAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_community_top_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.item.setText(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.pop_community_item_text);
        }
    }
}
