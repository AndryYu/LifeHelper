package com.android.andryyu.lifehelper.di.components;


import com.android.andryyu.lifehelper.di.modules.NetModule;
import com.android.andryyu.lifehelper.common.http.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Created by yufei on 2017/3/13.
 */
@Singleton
@Component(modules = NetModule.class)
public interface NetComponent {
    ApiService getApiService();
    OkHttpClient getOkHttp();
    Retrofit getRetrofit();
}
