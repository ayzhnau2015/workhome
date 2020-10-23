package com.thoughtworks.homework.domain.interator;


import com.thoughtworks.homework.data.DataSource;
import com.thoughtworks.homework.domain.excutor.PostExecutionThread;
import com.thoughtworks.homework.domain.excutor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T>{
    protected DataSource mDataSource;
    private ThreadExecutor mExecutor;
    private PostExecutionThread mMainThread;

    protected UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,DataSource dataSource){
        this.mExecutor = threadExecutor;
        this.mMainThread = postExecutionThread;
        mDataSource = dataSource;
    }

    public abstract Observable<T> buildUseCaseObservable();

    public void execute(Observer<T> useCaseSubscriber) {
        buildUseCaseObservable()
                .subscribeOn(Schedulers.from(mExecutor))
                .observeOn(mMainThread.getScheduler())
                .subscribe(useCaseSubscriber);
    }

    public abstract UseCase<T> get();
}

