package com.thoughtworks.homework.model;

import com.google.gson.annotations.SerializedName;

/**
 * message imgage url
 * @author zhuyaan
 * @since 2020-10-20
 */
public class ImageUrlBean {
    @SerializedName("url")
    private String mImageUrl;

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ImageUrlBean{" +
                "mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}
