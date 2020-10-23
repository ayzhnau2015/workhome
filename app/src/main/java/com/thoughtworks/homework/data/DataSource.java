package com.thoughtworks.homework.data;

import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Obtain data interface
 * @author zhuyaan
 * @since 2020-10-20
 */
public interface DataSource {
    /**
     * obtain current user info.
     * @return current user info.
     */
    Observable<UserBean> getUserInfo();

    /**
     * obtain message list start startindex and length
     * @param startIndex start position
     * @param length number of obtain
     * @return message list.
     */
    Observable<List<MessageBean>> getMessageList();
}
