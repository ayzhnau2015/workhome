package com.thoughtworks.homework.model;

import com.google.gson.annotations.SerializedName;

/**
 * userinfo model
 * @author zhuyaan
 * @since 2020-10-20
 */
public class UserBean extends SenderBean{
    @SerializedName("profile-image")
    private String mProfileImage;

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        mProfileImage = profileImage;
    }
}
