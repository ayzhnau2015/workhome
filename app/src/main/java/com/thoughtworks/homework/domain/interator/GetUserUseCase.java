package com.thoughtworks.homework.domain.interator;

import com.thoughtworks.homework.data.DataSource;
import com.thoughtworks.homework.domain.excutor.PostExecutionThread;
import com.thoughtworks.homework.domain.excutor.ThreadExecutor;
import com.thoughtworks.homework.model.UserBean;

import io.reactivex.Observable;

public class GetUserUseCase extends UseCase<UserBean> {

    public GetUserUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, DataSource dataSource) {
        super(threadExecutor, postExecutionThread, dataSource);
    }

    @Override
    public Observable<UserBean> buildUseCaseObservable() {
        return mDataSource.getUserInfo();
    }

    @Override
    public UseCase<UserBean> get() {
        return this;
    }
}
