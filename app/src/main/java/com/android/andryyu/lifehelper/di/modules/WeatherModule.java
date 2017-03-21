package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.mvp.view.WeatherContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/3/16.
 */
@Module
public class WeatherModule {
    private  WeatherContract.View mView;

    public WeatherModule(WeatherContract.View view) {
        mView = view;
    }

    @Provides
    WeatherContract.View provideWeatherModule() {
        return mView;
    }
}
