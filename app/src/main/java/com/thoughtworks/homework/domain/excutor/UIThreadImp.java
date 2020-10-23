package com.thoughtworks.homework.domain.excutor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UIThreadImp implements PostExecutionThread {

    public UIThreadImp(){}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
