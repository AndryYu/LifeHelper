package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.GithubLoginModule;
import com.android.andryyu.lifehelper.ui.act.LoginActivity;

import dagger.Component;

/**
 * Created by yufei on 2017/10/30.
 */
@UserScope
@Component(modules = GithubLoginModule.class, dependencies = NetComponent.class)
public interface GithubLoginComponent {
    void inject(LoginActivity activity);
}
