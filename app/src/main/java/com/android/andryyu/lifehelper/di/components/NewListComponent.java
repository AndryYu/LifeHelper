package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.NewListModule;
import com.android.andryyu.lifehelper.model.ditowine.ToutiaoFragment;

import dagger.Component;

/**
 * Created by yufei on 2017/10/30.
 */
@UserScope
@Component(modules = NewListModule.class, dependencies = NetComponent.class)
public interface NewListComponent {
    void inject(ToutiaoFragment activity);
}
