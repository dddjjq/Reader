package com.welson.reader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.reader.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityPopRecyclerAdapter extends RecyclerView.Adapter<CommunityPopRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<String> items;
    private int currentItem;
    private OnItemClickListener onItemClickListener;

    public CommunityPopRecyclerAdapter(Context context, List<String> items,int currentItem) {
        this.context = context;
        this.items = items;
        this.currentItem = currentItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_community_top_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int position = i;
        if (currentItem == i){
            viewHolder.item.setTextColor(context.getResources().getColor(R.color.colorMain));
        }else {
            viewHolder.item.setTextColor(Color.parseColor("#A3A3A3"));
        }
        viewHolder.item.setText(items.get(i));
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item;
        private RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.pop_community_item_text);
            layout = itemView.findViewById(R.id.pop_community_item_layout);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
