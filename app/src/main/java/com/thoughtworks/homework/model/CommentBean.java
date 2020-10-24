package com.thoughtworks.homework.model;

import com.google.gson.annotations.SerializedName;

/**
 * comment of other send message
 *
 * @author zhuyaan
 * @since 2020-10-20
 */
public class CommentBean {
    @SerializedName("content")
    private String mContent;
    @SerializedName("sender")
    private SenderBean mSenderBean;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public SenderBean getSenderBean() {
        return mSenderBean;
    }

    public void setSenderBean(SenderBean senderBean) {
        mSenderBean = senderBean;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "mContent='" + mContent + '\'' +
                ", mSenderBean=" + mSenderBean +
                '}';
    }
}
