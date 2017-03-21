package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.WeatherModule;
import com.android.andryyu.lifehelper.ui.act.WeatherActivity;

import dagger.Component;

/**
 * Created by yufei on 2017/3/16.
 */

@UserScope
@Component(modules = WeatherModule.class, dependencies = NetComponent.class)
public interface WeatherComponent {
    void inject(WeatherActivity activity);
}
