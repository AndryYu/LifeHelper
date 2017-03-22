package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.mvp.view.ZhiHuStoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/3/22.
 */
@Module
public class ZhiHuStoryModule {
    private  ZhiHuStoryContract.View mView;

    public ZhiHuStoryModule(ZhiHuStoryContract.View view) {
        mView = view;
    }

    @Provides
    ZhiHuStoryContract.View provideZhiHuStoryContract() {
        return mView;
    }
}
