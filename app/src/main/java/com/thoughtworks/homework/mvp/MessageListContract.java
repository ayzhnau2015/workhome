package com.thoughtworks.homework.mvp;

import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;

import java.util.List;

public interface MessageListContract {
    interface MessagePresenter {
        void start();

        void loadMore();
    }

    interface MessageView {
        void showUserInfo(UserBean userBean);

        void showAllMessage(List<MessageBean> messageBeans);

        void showLoading(int state);

        void goToFirstItem();
    }
}
