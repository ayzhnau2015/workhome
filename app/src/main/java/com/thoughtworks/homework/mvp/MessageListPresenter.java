package com.thoughtworks.homework.mvp;

import android.util.Log;

import com.thoughtworks.homework.data.DataSource;
import com.thoughtworks.homework.data.MovementDataSource;
import com.thoughtworks.homework.domain.excutor.ThreadExecutorImp;
import com.thoughtworks.homework.domain.excutor.UIThreadImp;
import com.thoughtworks.homework.domain.interator.GetMessageListUseCase;
import com.thoughtworks.homework.domain.interator.GetUserUseCase;
import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;
import com.thoughtworks.homework.util.MessageConstants;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MessageListPresenter implements MessageListContract.MessagePresenter {
    private static final String TAG = "MessageListPresenter";
    private GetUserUseCase mUserUseCase;
    private GetMessageListUseCase mMessageListUseCase;

    private MessageListContract.MessageView mView;
    private List<MessageBean> mAllMessage;
    private int mPage;
    public MessageListPresenter(MessageListContract.MessageView view){
        ThreadExecutorImp executorImp = new ThreadExecutorImp();
        UIThreadImp uiThreadImp = new UIThreadImp();
        DataSource dataSource = new MovementDataSource();
        mUserUseCase = new GetUserUseCase(executorImp,uiThreadImp,dataSource);
        mMessageListUseCase = new GetMessageListUseCase(executorImp,uiThreadImp,dataSource);
        mView = view;
    }
    @Override
    public void start() {
        mUserUseCase.get().execute(new Observer<UserBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull UserBean userBean) {
                if(userBean == null){
                    Log.w(TAG,"obtain user info is null.");
                    return ;
                }
                mView.showUserInfo(userBean);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG,"obtain user info error." + e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });
        mMessageListUseCase.get().execute(new Observer<List<MessageBean>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<MessageBean> messageBeans) {
                mAllMessage = messageBeans;
                mView.showAllMessage(messageBeans.subList(0,5));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG,"obtain user info error." + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void loadMore() {
        int totalPage = 0;
        int other = mAllMessage.size() % 5;
        if(other != 0){
            totalPage = mAllMessage.size() / 5 + 1;
        } else {
            totalPage = mAllMessage.size() / 5;
        }
        if(mPage == totalPage){
            mView.showLoading(MessageConstants.LOADING_NO_MORE);
            return ;
        }
        mPage++;
        int start = mPage * 5;
        int end = start + 5;
        mView.showAllMessage(mAllMessage.subList(start,end));
    }
}
