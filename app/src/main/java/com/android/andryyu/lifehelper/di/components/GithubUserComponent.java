package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.GithubUserModule;
import com.android.andryyu.lifehelper.model.mine.MineFragment;

import dagger.Component;

/**
 * Created by yufei on 2017/10/30.
 */
@UserScope
@Component(modules = GithubUserModule.class, dependencies = NetComponent.class)
public interface GithubUserComponent {
    void inject(MineFragment fragment);
}
