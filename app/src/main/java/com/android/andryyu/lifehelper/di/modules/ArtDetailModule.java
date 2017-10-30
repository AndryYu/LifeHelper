package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.mvp.view.ArtDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/10/29.
 */
@Module
public class ArtDetailModule {
    private ArtDetailContract.View mView;

    public ArtDetailModule(ArtDetailContract.View view) {
        mView = view;
    }

    @Provides
    ArtDetailContract.View provideArtDetailContract() {
        return mView;
    }
}
