package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.model.havefun.mvp.contract.OpenEyesContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/3/21.
 */
@Module
public class OpenEyesModule {
    private  OpenEyesContract.View mView;

    public OpenEyesModule(OpenEyesContract.View view) {
        mView = view;
    }

    @Provides
    OpenEyesContract.View provideOpenEyesContract() {
        return mView;
    }
}
