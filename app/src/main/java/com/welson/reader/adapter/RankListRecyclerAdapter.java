package com.welson.reader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.entity.RankingList;
import com.welson.reader.view.RankLayout;

import java.util.ArrayList;

public class RankListRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private RankingList rankingList;
    private static final int TYPE_GENDER = 0x01;
    private static final int TYPE_MALE = 0x02;
    private static final int TYPE_FEMALE = 0x03;
    private static final int TYPE_COLLAPSE_MALE = 0x04;
    private static final int TYPE_COLLAPSE_FEMALE = 0x05;
    private int females;
    private int males;

    public RankListRecyclerAdapter(Context context, RankingList rankingList){
        this.context = context;
        this.rankingList = rankingList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType){
            case TYPE_GENDER:
                view = LayoutInflater.from(context).inflate(R.layout.rank_gender_item,viewGroup,false);
                return new GenderViewHolder(view);
            case TYPE_MALE:
            case TYPE_FEMALE:
                view = LayoutInflater.from(context).inflate(R.layout.rank_list_item_layout,viewGroup,false);
                return new NormalViewHolder(view);
            case TYPE_COLLAPSE_MALE:
            case TYPE_COLLAPSE_FEMALE:
                view = LayoutInflater.from(context).inflate(R.layout.rank_gender_item,viewGroup,false);
                return new GenderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int malePosition = getRealMalePosition(position);
        int femalePosition = getRealFemalePosition(position);
        switch (getItemViewType(position)){
            case TYPE_GENDER:
                GenderViewHolder genderViewHolder = (GenderViewHolder)viewHolder;
                if (position == 1){
                    genderViewHolder.genderText.setText(context.getResources().getText(R.string.str_rank_male));
                }else {
                    genderViewHolder.genderText.setText(context.getResources().getText(R.string.str_rank_female));
                }
                break;
            case TYPE_MALE:
                NormalViewHolder normalViewHolder = (NormalViewHolder)viewHolder;
                normalViewHolder.rankLayout.setItemString(rankingList.getMale()
                        .get(malePosition).getTitle());
                normalViewHolder.rankLayout.setImage(rankingList.getMale()
                        .get(malePosition).getCover());
                break;
            case TYPE_FEMALE:
                NormalViewHolder normalViewHolder1 = (NormalViewHolder)viewHolder;
                normalViewHolder1.rankLayout.setItemString(rankingList.getFemale()
                        .get(malePosition).getTitle());
                normalViewHolder1.rankLayout.setImage(rankingList.getFemale()
                        .get(malePosition).getCover());
                break;
            case TYPE_COLLAPSE_MALE:
                GenderViewHolder genderViewHolder1 = (GenderViewHolder)viewHolder;
                genderViewHolder1.genderText.setText("test");
                break;
            case TYPE_COLLAPSE_FEMALE:
                GenderViewHolder genderViewHolder2 = (GenderViewHolder)viewHolder;
                genderViewHolder2.genderText.setText("test");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return males + females + 4;
    }

    @Override
    public int getItemViewType(int position) {
        for (RankingList.Rank r : rankingList.getMale()){
            if (!r.isCollapse()){
                males++;
            }
        }
        for (RankingList.Rank r : rankingList.getFemale()){
            if (!r.isCollapse()){
                females++;
            }
        }
        if (position == 0 || position == (males+2)){
            return TYPE_GENDER;
        }else if (position == (males + 1)){
            return TYPE_COLLAPSE_MALE;
        }else if (position == (males + females + 3)){
            return TYPE_COLLAPSE_FEMALE;
        }else if (position > 0 && position < (males + 1)){
            return TYPE_MALE;
        }else{
            return TYPE_FEMALE;
        }
    }

    private int getRealMalePosition(int position){
        return position -1;
    }

    private int getRealFemalePosition(int position){
        return position - 2 - males;
    }

    class NormalViewHolder extends RecyclerView.ViewHolder{

        private RankLayout rankLayout;

        NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            rankLayout = itemView.findViewById(R.id.rank_list_item);
        }
    }

    class GenderViewHolder extends RecyclerView.ViewHolder{

        private TextView genderText;

        GenderViewHolder(@NonNull View itemView) {
            super(itemView);
            genderText = itemView.findViewById(R.id.gender_text);
        }
    }

    class CollapseViewHolder extends RecyclerView.ViewHolder{

        CollapseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
