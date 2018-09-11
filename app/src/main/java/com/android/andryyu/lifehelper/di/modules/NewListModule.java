package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.model.ditowine.mvp.contract.NewListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/10/30.
 */
@Module
public class NewListModule {
    private NewListContract.View mView;

    public NewListModule(NewListContract.View view) {
        mView = view;
    }

    @Provides
    NewListContract.View provideContract() {
        return mView;
    }
}
