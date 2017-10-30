package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.mvp.view.GithubLoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/10/30.
 */

@Module
public class GithubLoginModule {
    private GithubLoginContract.View mView;

    public GithubLoginModule(GithubLoginContract.View view) {
        mView = view;
    }

    @Provides
    GithubLoginContract.View provideGithubContract() {
        return mView;
    }
}
