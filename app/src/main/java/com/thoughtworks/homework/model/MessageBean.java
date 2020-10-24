package com.thoughtworks.homework.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * every message infomation
 * @author zhuyaan
 * @since 2020-10-20
 */
public class MessageBean {
    @SerializedName("content")
    private String mContent;
    @SerializedName("sender")
    private SenderBean mSenderBean;
    @SerializedName("images")
    private List<ImageUrlBean> mImageUrlBeanList;
    @SerializedName("comments")
    private List<CommentBean> mCommentBeanList;
    @SerializedName(value = "error",alternate = "unknown error")
    private String mErrorMsg;
    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<ImageUrlBean> getImageUrlBeanList() {
        return mImageUrlBeanList;
    }

    public void setImageUrlBeanList(List<ImageUrlBean> imageUrlBeanList) {
        mImageUrlBeanList = imageUrlBeanList;
    }

    public SenderBean getSenderBean() {
        return mSenderBean;
    }

    public void setSenderBean(SenderBean senderBean) {
        mSenderBean = senderBean;
    }

    public List<CommentBean> getCommentBeanList() {
        return mCommentBeanList;
    }

    public void setCommentBeanList(List<CommentBean> commentBeanList) {
        mCommentBeanList = commentBeanList;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
    }

    /**
     * 判断是否有效
     * @return true 有效 false 无效
     */
    public boolean isLegal(){
        return !TextUtils.isEmpty(mContent) || (mImageUrlBeanList != null && mImageUrlBeanList.size() > 0);
    }
    @Override
    public String toString() {
        return "MessageBean{" +
                "mContent='" + mContent + '\'' +
                ", mSenderBean=" + mSenderBean +
                ", mImageUrlBeanList=" + mImageUrlBeanList +
                ", mCommentBeanList=" + mCommentBeanList +
                ", mErrorMsg='" + mErrorMsg + '\'' +
                '}';
    }
}
