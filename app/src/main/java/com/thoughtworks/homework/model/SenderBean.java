package com.thoughtworks.homework.model;

import com.google.gson.annotations.SerializedName;

/**
 * who is send message to movement
 *
 * @author zhuyaan
 * @since 2020-10-20
 */
public class SenderBean {
    @SerializedName("avatar")
    private String mAvatar;
    @SerializedName("nick")
    private String mNick;
    @SerializedName("username")
    private String mUserName;

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getNick() {
        return mNick;
    }

    public void setNick(String nick) {
        mNick = nick;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }
}
