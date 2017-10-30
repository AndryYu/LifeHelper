package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.OpenEyesModule;
import com.android.andryyu.lifehelper.ui.fragment.VideoFragment;
import com.android.andryyu.lifehelper.ui.fragment.home.OpenEyesFragment;

import dagger.Component;

/**
 * Created by yufei on 2017/3/21.
 */
@UserScope
@Component(modules = OpenEyesModule.class, dependencies = NetComponent.class)
public interface OpenEyesComponent {
    void inject(OpenEyesFragment fragment);
    void inject(VideoFragment fragment);
}
