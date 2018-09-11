package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.model.foodcloth.mvp.contract.OwnspaceContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/3/21.
 */
@Module
public class OwnspaceModule {
    private OwnspaceContract.View mView;

    public OwnspaceModule(OwnspaceContract.View view) {
        mView = view;
    }

    @Provides
    OwnspaceContract.View provideOwnspaceContract() {
        return mView;
    }
}
