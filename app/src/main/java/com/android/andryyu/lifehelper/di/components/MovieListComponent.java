package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.MovieListModule;
import com.android.andryyu.lifehelper.model.ditowine.MovieFragment;

import dagger.Component;

/**
 * Created by yufei on 2017/10/30.
 */
@UserScope
@Component(modules = MovieListModule.class, dependencies = NetComponent.class)
public interface MovieListComponent {
    void inject(MovieFragment activity);
}
