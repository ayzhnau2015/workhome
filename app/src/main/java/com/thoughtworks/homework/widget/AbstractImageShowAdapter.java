package com.thoughtworks.homework.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractImageShowAdapter {
    protected abstract int getItemCount();

    protected abstract View createView(LayoutInflater inflater, ViewGroup parent, int position);

    protected abstract void bindView(View view, int position);

    public void OnItemClick(int position, View view) {
    }

    public String getUrl(int index){
        return null;
    }
}
