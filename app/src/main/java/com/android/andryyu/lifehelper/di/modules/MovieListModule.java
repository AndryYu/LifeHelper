package com.android.andryyu.lifehelper.di.modules;

import com.android.andryyu.lifehelper.model.ditowine.mvp.contract.MovieListContract;
import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/10/30.
 */
@Module
public class MovieListModule {
    private MovieListContract.View mView;

    public MovieListModule(MovieListContract.View view) {
        mView = view;
    }

    @Provides
    MovieListContract.View provideGithubContract() {
        return mView;
    }
}
