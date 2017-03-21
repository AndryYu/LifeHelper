package com.android.andryyu.lifehelper.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yufei on 2017/3/14.
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context){
        this.mContext = context;
    }

    @Provides
    Context provideContext(){
        return mContext;
    }
}
