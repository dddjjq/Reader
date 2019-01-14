package com.welson.reader.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welson.reader.R;
import com.welson.reader.base.OnRefreshListener;
import com.welson.reader.constant.Constants;
import com.welson.reader.entity.DiscussionList;
import com.welson.reader.util.GlideUtil;
import com.welson.reader.util.TimeUtil;
import com.welson.reader.view.CircleImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommunityRecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<DiscussionList.Post> posts;
    private Context context;
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_END = 2;
    private OnRefreshListener onRefreshListener;
    private RecyclerView recyclerView;

    public CommunityRecyclerAdapter(ArrayList<DiscussionList.Post> posts, Context context, RecyclerView recyclerView) {
        this.context = context;
        this.posts = posts;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(context).inflate(R.layout.community_detail_top, viewGroup, false);
            return new TopViewHolder(view);
        } else if (viewType == TYPE_END) {
            View view = LayoutInflater.from(context).inflate(R.layout.community_detail_bottom, viewGroup, false);
            return new BottomViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.community_detail_item, viewGroup, false);
            return new NormalViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)) {
            case TYPE_HEAD:
                break;
            case TYPE_NORMAL:
                if (posts.size() == 0)
                    return;
                final int truePosition = i - 1;
                NormalViewHolder normalViewHolder = (NormalViewHolder) viewHolder;
                GlideUtil.loadImage(context, Constants.IMG_BASE_URL + posts.get(truePosition).getAuthor().getAvatar(), normalViewHolder.itemImage);
                normalViewHolder.author.setText(getAuthorString(posts.get(truePosition).getAuthor().getNickname(), posts.get(truePosition).getAuthor().getLv()));
                normalViewHolder.title.setText(posts.get(truePosition).getTitle());
                if (posts.get(truePosition).getType().equals("normal")) {
                    normalViewHolder.commentCount.setText(String.valueOf(posts.get(truePosition).getCommentCount()));
                    normalViewHolder.commentImage.setImageResource(R.drawable.community_item_comment);
                } else {
                    normalViewHolder.commentCount.setText(String.valueOf(posts.get(truePosition).getVoteCount()));
                    normalViewHolder.commentImage.setImageResource(R.drawable.ic_notif_vote);
                }
                normalViewHolder.loveCount.setText(String.valueOf(posts.get(truePosition).getLikeCount()));
                normalViewHolder.time.setText(TimeUtil.getDescriptionTimeFromDateString(posts.get(truePosition).getCreated()));
                break;
            case TYPE_END:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == posts.size() + 1) {
            return TYPE_END;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return posts.size() + 2;
    }

    private String getAuthorString(String author, int level) {
        return author + " lv." + level;
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView itemImage;
        private TextView author;
        private TextView time;
        private TextView title;
        private ImageView commentImage;
        private ImageView loveImage;
        private TextView commentCount;
        private TextView loveCount;

        public NormalViewHolder(@NonNull View itemView) {
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

    public class TopViewHolder extends RecyclerView.ViewHolder {

        private ImageView arrowImage;
        private RelativeLayout arrowLayout;
        private ObjectAnimator animator;

        TopViewHolder(@NonNull View itemView) {
            super(itemView);
            arrowImage = itemView.findViewById(R.id.community_item_top_image);
            arrowLayout = itemView.findViewById(R.id.community_top_arrow_layout);
            animator = ObjectAnimator.ofFloat(arrowImage, "rotation", 0, 360);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setDuration(1000);
        }

        public void setTopHeight(float height) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (height < 300) {
                params.height = (int) height;
                itemView.setLayoutParams(params);
            }
            if (height < 200) {
                arrowImage.setImageResource(R.drawable.community_arrow_down);
            } else {
                arrowImage.setImageResource(R.drawable.community_arrow_up);
            }
        }

        public void releaseArrow(float height) {
            if (height < 200) {
                setTopHeight(0);
                recyclerView.scrollToPosition(1);
            } else {
                arrowImage.setImageResource(R.drawable.community_top_load);
                onRefreshListener.onRefresh();
                startAnimation();
            }
        }

        private void startAnimation() {
            animator.start();
        }

        public void abortAnimation() {
            animator.cancel();
            setTopHeight(0);
            recyclerView.scrollToPosition(1);
            arrowImage.setRotation(0); //防止属性动画旋转 类似于属性动画用反射修改rotation.
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {

        private ImageView loadImage;
        private RelativeLayout loadLayout;
        private ObjectAnimator animator;

        BottomViewHolder(@NonNull View itemView) {
            super(itemView);
            loadImage = itemView.findViewById(R.id.community_item_bottom_image);
            loadLayout = itemView.findViewById(R.id.community_bottom_arrow_layout);
            animator = ObjectAnimator.ofFloat(loadImage, "rotation", 0, 360);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setDuration(1000);
        }

        public void setTopHeight(float height) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (height < 200) {
                params.height = (int) height;
                itemView.setLayoutParams(params);
            }
        }

        public void releaseLoad(float height) {
            if (height < 100) {
                setTopHeight(0);
                loadImage.setVisibility(View.GONE);
                animator.cancel();
            } else {
                setTopHeight(200);
                onRefreshListener.onLoadMore();
                loadImage.setVisibility(View.VISIBLE);
                animator.start();
            }
        }

        public void abortAnimation() {
            animator.cancel();
            setTopHeight(0);
            loadImage.setRotation(0); //防止属性动画旋转 类似于属性动画用反射修改rotation.
        }
    }


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
