package com.android.andryyu.lifehelper.di.components;

import com.android.andryyu.lifehelper.di.UserScope;
import com.android.andryyu.lifehelper.di.modules.OwnspaceModule;
import com.android.andryyu.lifehelper.model.foodcloth.FoodClothFragment;

import dagger.Component;

/**
 * Created by yufei on 2017/3/21.
 */
@UserScope
@Component(modules = OwnspaceModule.class, dependencies = NetComponent.class)
public interface OwnspaceComponent {
    void inject(FoodClothFragment fragment);
}
