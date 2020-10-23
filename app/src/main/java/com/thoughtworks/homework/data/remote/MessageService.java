package com.thoughtworks.homework.data.remote;

import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MessageService {
    @GET("user/jsmith/tweets")
    Observable<List<MessageBean>> getMessageList();

    @GET("user/jsmith")
    Observable<UserBean> getUserInfo();
}
