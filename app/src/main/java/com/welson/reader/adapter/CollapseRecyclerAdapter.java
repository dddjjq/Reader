package com.welson.reader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.RankingList;
import com.welson.reader.util.GlideUtil;

import java.util.ArrayList;

public class CollapseRecyclerAdapter extends RecyclerView.Adapter<CollapseRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RankingList.Rank> ranks;

    public CollapseRecyclerAdapter(Context context, ArrayList<RankingList.Rank> ranks) {
        this.context = context;
        this.ranks = ranks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rank_collapse_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL + ranks.get(i).getCover(), viewHolder.collapseItemImage);
        viewHolder.collapseItemText.setText(ranks.get(i).getTitle());
        viewHolder.collapseItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return ranks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout collapseItemLayout;
        private ImageView collapseItemImage;
        private TextView collapseItemText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            collapseItemLayout = itemView.findViewById(R.id.rank_collapse_item_layout);
            collapseItemImage = itemView.findViewById(R.id.rank_collapse_item_image);
            collapseItemText = itemView.findViewById(R.id.rank_collapse_item_text);
        }
    }
}
