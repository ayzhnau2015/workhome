package com.thoughtworks.homework.mvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.homework.R;
import com.thoughtworks.homework.model.CommentBean;
import com.thoughtworks.homework.model.ImageUrlBean;
import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.SenderBean;
import com.thoughtworks.homework.util.GlideUtil;
import com.thoughtworks.homework.util.MessageConstants;
import com.thoughtworks.homework.widget.LinearCommentLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

public class MessageListAdapter extends RecyclerView.Adapter{
    public static final String TAG = "MessageListAdapter";
    public static final int LOAD_VIEW_TYPE = 0;
    public static final int MESSAGE_VIEW_TYPE = 1;
    private Context mContext;
    private List<MessageBean> mMessageBeans;
    private LayoutInflater mLayoutInflater;
    private ProgressBar mProgressBar;
    private TextView mLoadTextView;
    public MessageListAdapter(Context context,List<MessageBean> messageBeans){
        mContext = context;
        mMessageBeans = messageBeans;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_VIEW_TYPE) {
            return new MessageHolder(mLayoutInflater.inflate(R.layout.item_movement, parent, false));
        } else {
            return new LoadViewHolder(mLayoutInflater.inflate(R.layout.item_recyclerview_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int type = getItemViewType(position);
        if(type == MESSAGE_VIEW_TYPE){
            MessageHolder holder = (MessageHolder) viewHolder;
            bindData(holder,mMessageBeans.get(position));
        } else {
            LoadViewHolder loadViewHolder = (LoadViewHolder) viewHolder;
            loadViewHolder.itemView.setVisibility(View.VISIBLE);
        }
    }

    private void bindData(MessageHolder holder, MessageBean messageBean) {
        if(messageBean == null){
            Log.w(TAG,"message is null.");
            return ;
        }
        List<ImageUrlBean> imageUrlBeanList = messageBean.getImageUrlBeanList();
        List<CommentBean> commentBeanList = messageBean.getCommentBeanList();
        if(TextUtils.isEmpty(messageBean.getContent())){
            holder.tvDesc.setVisibility(View.GONE);
        } else {
            holder.tvDesc.setVisibility(View.VISIBLE);
            holder.tvDesc.setText(messageBean.getContent());
        }

        SenderBean sender = messageBean.getSenderBean();
        if(sender != null){
            holder.tvName.setText(sender.getUserName());
            GlideUtil.loadRoundedCorner(mContext,sender.getAvatar(), holder.ivHead, R.mipmap.ic_launcher);
            holder.ivHead.setOnClickListener(view -> {
                if (!TextUtils.isEmpty(sender.getAvatar())) {
                    //CustomBitmapActivity.navigateToCustomBitmapActivity(mContext, avatar, true);
                    Log.i(TAG,"click head.");
                } else {
                    Toast.makeText(mContext,R.string.error_msg,Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(imageUrlBeanList == null || imageUrlBeanList.size() == 0){
            holder.mImageShowLayout.setVisibility(View.GONE);
        } else {
            holder.mImageShowLayout.setVisibility(View.VISIBLE);
            List<ImageUrlBean> urlBeanList = messageBean.getImageUrlBeanList();
            ArrayList<String> strUrlList = new ArrayList<>();
            for (ImageUrlBean urlBean : urlBeanList) {
                strUrlList.add(urlBean.getImageUrl());
            }
            holder.mImageShowLayout.setData(strUrlList);
        }

        if(commentBeanList == null || commentBeanList.size() == 0){
            holder.mCommentLayout.setVisibility(View.GONE);
        } else {
            holder.mCommentLayout.setVisibility(View.VISIBLE);
            holder.mCommentLayout.setCommentBeanList(commentBeanList);
            holder.mCommentLayout.showCommit();
        }

        holder.tvTime.setText("2020-10-20 16:40");
    }

    @Override
    public int getItemViewType(int position) {
        //last item
        if(getItemCount() == position + 1){
            return LOAD_VIEW_TYPE;
        } else {
            return MESSAGE_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageBeans == null ? 0 : mMessageBeans.size();
    }

    public void setFootView(int loadingState) {
        if (loadingState == MessageConstants.LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
            mLoadTextView.setVisibility(View.VISIBLE);
            mLoadTextView.setText(mContext.getString(R.string.load_more));
        }  else if (loadingState == MessageConstants.LOADING_COMPLETE) {
            mProgressBar.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        } else if (loadingState == MessageConstants.LOADING_NO_MORE) {
            mProgressBar.setVisibility(View.GONE);
            mLoadTextView.setVisibility(View.VISIBLE);
            mLoadTextView.setText(mContext.getString(R.string.no_more));
        }
    }

    static class MessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.npl_item_moment_photos)
        BGANinePhotoLayout mImageShowLayout;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.rl_comment)
        View commentRoot;
        @BindView(R.id.cv)
        LinearCommentLayout mCommentLayout;
        @BindView(R.id.tv_time)
        TextView tvTime;


        public MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private class LoadViewHolder extends RecyclerView.ViewHolder {
        public LoadViewHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.we_media_progress);
            mLoadTextView = itemView.findViewById(R.id.we_media_loading);
        }
    }
}
