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
    protected String mAvatar;
    @SerializedName("nick")
    protected String mNick;
    @SerializedName("username")
    protected String mUserName;

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

    @Override
    public String toString() {
        return "SenderBean{" +
                "mAvatar='" + mAvatar + '\'' +
                ", mNick='" + mNick + '\'' +
                ", mUserName='" + mUserName + '\'' +
                '}';
    }
}
