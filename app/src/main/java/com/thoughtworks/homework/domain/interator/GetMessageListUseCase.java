package com.thoughtworks.homework.domain.interator;

import com.thoughtworks.homework.data.DataSource;
import com.thoughtworks.homework.domain.excutor.PostExecutionThread;
import com.thoughtworks.homework.domain.excutor.ThreadExecutor;
import com.thoughtworks.homework.model.MessageBean;

import java.util.List;

import io.reactivex.Observable;

public class GetMessageListUseCase extends UseCase<List<MessageBean>> {

    public GetMessageListUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, DataSource dataSource) {
        super(threadExecutor, postExecutionThread, dataSource);
    }

    @Override
    public Observable<List<MessageBean>> buildUseCaseObservable() {
        return mDataSource.getMessageList();
    }


    @Override
    public UseCase<List<MessageBean>> get() {
        return this;
    }
}
