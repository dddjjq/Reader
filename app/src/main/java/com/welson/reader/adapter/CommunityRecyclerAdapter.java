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
import com.welson.reader.entity.DiscussionList;
import com.welson.reader.util.GlideUtil;
import com.welson.reader.view.CircleImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommunityRecyclerAdapter extends RecyclerView.Adapter<CommunityRecyclerAdapter.ViewHolder> {

    private ArrayList<DiscussionList.Post> posts;
    private Context context;

    public CommunityRecyclerAdapter(ArrayList<DiscussionList.Post> posts, Context context){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_detail_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GlideUtil.loadImage(context, Constants.IMG_BASE_URL + posts.get(i).getAuthor().getAvatar(),viewHolder.itemImage);
        viewHolder.author.setText(getAuthorString(posts.get(i).getAuthor().getNickname(),posts.get(i).getAuthor().getLv()));
        viewHolder.title.setText(posts.get(i).getTitle());
        if (posts.get(i).getType().equals("normal")){
            viewHolder.commentCount.setText(String.valueOf(posts.get(i).getCommentCount()));
            viewHolder.commentImage.setImageResource(R.drawable.community_item_comment);
        }else {
            viewHolder.commentCount.setText(String.valueOf(posts.get(i).getVoteCount()));
            viewHolder.commentImage.setImageResource(R.drawable.ic_notif_vote);
        }
        viewHolder.loveCount.setText(String.valueOf(posts.get(i).getLikeCount()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private String getAuthorString(String author,int level){
        return author + " lv." + level;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView itemImage;
        private TextView author;
        private TextView time;
        private TextView title;
        private ImageView commentImage;
        private ImageView loveImage;
        private TextView commentCount;
        private TextView loveCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.community_detail_image);
            author = itemView.findViewById(R.id.community_detail_author);
            time = itemView.findViewById(R.id.community_detail_time);
            title = itemView.findViewById(R.id.community_detail_title);
            commentImage = itemView.findViewById(R.id.community_item_comment_image);
            loveImage = itemView.findViewById(R.id.community_item_love_image);
            commentCount = itemView.findViewById(R.id.community_item_comment_count);
            loveCount = itemView.findViewById(R.id.community_item_love_count);
        }
    }
}
