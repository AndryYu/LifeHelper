package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.ZhiHuModule;
import com.android.andryyu.lifehelper.model.ditowine.ZhiHuFragment;

import dagger.Component;

/**
 * Created by yufei on 2017/3/21.
 */
@UserScope
@Component(modules = ZhiHuModule.class, dependencies = NetComponent.class)
public interface ZhiHuComponent {
    void inject(ZhiHuFragment fragment);
}
