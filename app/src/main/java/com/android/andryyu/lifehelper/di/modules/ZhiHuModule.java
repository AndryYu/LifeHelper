package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.model.ditowine.mvp.contract.ZhiHuContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/3/21.
 */
@Module
public class ZhiHuModule {
    private ZhiHuContract.View mView;

    public ZhiHuModule(ZhiHuContract.View view) {
        mView = view;
    }

    @Provides
    ZhiHuContract.View provideZhiHuContract() {
        return mView;
    }
}
