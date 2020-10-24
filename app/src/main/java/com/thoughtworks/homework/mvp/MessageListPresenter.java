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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MessageListPresenter implements MessageListContract.MessagePresenter {
    private static final int MAX_PAGE_SIZE = 5;
    private static final String TAG = "MessageListPresenter";
    private GetUserUseCase mUserUseCase;
    private GetMessageListUseCase mMessageListUseCase;

    private MessageListContract.MessageView mView;
    private List<MessageBean> mAllMessage;
    private int mTotalPage;
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
                Log.w(TAG,"obtain user info is userBean." + userBean);
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
                Log.e(TAG,"obtain user onNext.");
                mAllMessage = deleteIllegal(messageBeans);
                int other = mAllMessage.size() % MAX_PAGE_SIZE;
                if(other != 0){
                    mTotalPage = mAllMessage.size() / MAX_PAGE_SIZE + 1;
                } else {
                    mTotalPage = mAllMessage.size() / MAX_PAGE_SIZE;
                }
                mView.showAllMessage(mAllMessage.subList(0,MAX_PAGE_SIZE));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG,"obtain user info error." + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"obtain user onComplete.");
            }
        });
    }

    private List<MessageBean> deleteIllegal(List<MessageBean> messageBeans) {
        List<MessageBean> resultList = new ArrayList<>();
        if(messageBeans == null || messageBeans.size() == 0){
            return resultList;
        }
        for (MessageBean messageBean : messageBeans) {
            if(messageBean != null && messageBean.isLegal()){
                resultList.add(messageBean);
            }
        }
        return resultList;
    }

    @Override
    public void loadMore() {
        if(mAllMessage == null){
            Log.w(TAG,"LOAD MORE LIST IS NULL.");
            return ;
        }
        if(mPage < mTotalPage){
            mPage++;
            int end = mPage * MAX_PAGE_SIZE;
            int start = end - MAX_PAGE_SIZE;

            mView.showAllMessage(mAllMessage.subList(start, end));
        } else {
            mView.showLoading(MessageConstants.LOADING_NO_MORE);
        }
    }
}
