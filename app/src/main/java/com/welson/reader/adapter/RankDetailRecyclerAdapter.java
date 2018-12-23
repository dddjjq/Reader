package com.welson.reader.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.activity.BookDetailActivity;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.Rankings;
import com.welson.reader.util.GlideUtil;

import java.util.ArrayList;

public class RankDetailRecyclerAdapter extends RecyclerView.Adapter<RankDetailRecyclerAdapter.ViewHolder> {


    private ArrayList<Rankings.Book> books;
    private Context context;

    public RankDetailRecyclerAdapter(Context context, ArrayList<Rankings.Book> books){
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rank_detail_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.rankDetailTitle.setText(books.get(i).getTitle());
        viewHolder.rankDetailAuthor.setText(books.get(i).getAuthor() + context.getString(R.string.str_rank_detail_author));
        viewHolder.rankDetailChapter.setText(getTrueString(books.get(i).getShortIntro()).trim());
        viewHolder.rankDetailReader.setText(getSaveString(books.get(i).getLatelyFollower(),books.get(i).getRetentionRatio()));
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL+books.get(i).getCover(),viewHolder.rankDetailImage);
        viewHolder.rankDetailItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BookDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    private String getSaveString(int chase,String save){
        return chase + context.getString(R.string.str_rank_detail_chase)
                + context.getString(R.string.str_rank_detail_author)
                + save + context.getString(R.string.str_rank_detail_save);
    }

    private String getTrueString(String save){
        return save.replaceAll("\n","");
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout rankDetailItemLayout;
        private ImageView rankDetailImage;
        private TextView rankDetailTitle;
        private TextView rankDetailAuthor;
        private TextView rankDetailChapter;
        private TextView rankDetailReader;

        private
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rankDetailItemLayout = itemView.findViewById(R.id.rank_detail_item);
            rankDetailImage = itemView.findViewById(R.id.rank_detail_image);
            rankDetailTitle = itemView.findViewById(R.id.rank_detail_title);
            rankDetailAuthor = itemView.findViewById(R.id.rank_detail_author);
            rankDetailChapter = itemView.findViewById(R.id.rank_detail_chapter);
            rankDetailReader = itemView.findViewById(R.id.rank_detail_reader);
        }
    }
}
