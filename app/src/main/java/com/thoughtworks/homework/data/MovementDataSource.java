package com.thoughtworks.homework.data;

import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;

import java.util.List;

import io.reactivex.Observable;

public class MovementDataSource implements DataSource {
    @Override
    public Observable<UserBean> getUserInfo() {
        return null;
    }

    @Override
    public Observable<List<MessageBean>> getMessageList() {
        return null;
    }
}
