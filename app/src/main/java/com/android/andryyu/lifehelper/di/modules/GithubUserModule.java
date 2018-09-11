package com.android.andryyu.lifehelper.di.modules;

import android.content.Context;

import com.android.andryyu.lifehelper.model.mine.mvp.contract.GithubUserContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/10/30.
 */
@Module
public class GithubUserModule {
    private GithubUserContract.View mView;
    private Context mContext;

    public GithubUserModule(Context context, GithubUserContract.View view) {
        mContext = context;
        mView = view;
    }

    @Provides
    GithubUserContract.View provideGithubContract() {
        return mView;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
