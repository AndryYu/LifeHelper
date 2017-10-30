package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.ArtDetailModule;
import com.android.andryyu.lifehelper.di.modules.OpenEyesModule;
import com.android.andryyu.lifehelper.ui.act.ArtDetailActivity;
import com.android.andryyu.lifehelper.ui.fragment.home.OpenEyesFragment;

import dagger.Component;

/**
 * Created by yufei on 2017/10/29.
 */
@UserScope
@Component(modules = ArtDetailModule.class, dependencies = NetComponent.class)
public interface ArtDetailComponent {
    void inject(ArtDetailActivity fragment);
}
