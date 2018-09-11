package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.ZhiHuStoryModule;
import com.android.andryyu.lifehelper.model.ditowine.ui.ZhiHuStoryActivity;

import dagger.Component;

/**
 * Created by yufei on 2017/3/22.
 */
@UserScope
@Component(modules = ZhiHuStoryModule.class, dependencies = NetComponent.class)
public interface ZhiHuStoryComponent {
    void inject(ZhiHuStoryActivity activity);
}
