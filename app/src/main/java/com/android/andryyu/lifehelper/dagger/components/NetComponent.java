package com.android.andryyu.lifehelper.dagger.components;


import com.android.andryyu.lifehelper.dagger.modules.NetModule;
import com.android.andryyu.lifehelper.http.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Created by yufei on 2017/3/13.
 */
@Component(modules = NetModule.class)
@Singleton
public interface NetComponent {
    ApiService getApiService();
    OkHttpClient getOkHttp();
    Retrofit getRetrofit();
}
