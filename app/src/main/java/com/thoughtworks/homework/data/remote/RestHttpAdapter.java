package com.thoughtworks.homework.data.remote;


import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.thoughtworks.homework.util.MessageConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestHttpAdapter {
    public static final int TIME_OUT_UNIT = 30 * 1000;
    private Retrofit.Builder retrofitBuilder;

    public Retrofit getRetrofit(String baseUrl) {
        String url = baseUrl;
        if(TextUtils.isEmpty(url)){
            url = MessageConstants.MESSAGE_URL;
        }
        if (null != retrofitBuilder) {
            retrofitBuilder.baseUrl(url);
        } else {
            retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(getBuilder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        return retrofitBuilder.build();
    }

    private OkHttpClient.Builder getBuilder(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT_UNIT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT_UNIT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT_UNIT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE));
        return builder;
    }
}
