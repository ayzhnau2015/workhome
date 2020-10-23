package com.thoughtworks.homework.data;

import com.thoughtworks.homework.data.remote.MessageService;
import com.thoughtworks.homework.data.remote.RestHttpAdapter;
import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;
import com.thoughtworks.homework.util.MessageConstants;

import java.util.List;

import io.reactivex.Observable;

public class MovementDataSource implements DataSource {
    private MessageService mMessageService;

    public MovementDataSource(){
        RestHttpAdapter restHttpAdapter = new RestHttpAdapter();
        mMessageService = restHttpAdapter
                .getRetrofit(MessageConstants.MESSAGE_URL)
                .create(MessageService.class);
    }
    @Override
    public Observable<UserBean> getUserInfo() {
        return mMessageService.getUserInfo();
    }

    @Override
    public Observable<List<MessageBean>> getMessageList() {
        return mMessageService.getMessageList();
    }
}
