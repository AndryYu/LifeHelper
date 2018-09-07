package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.zhihu.Story;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.ZhiHuStoryContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yufei on 2017/3/22.
 */

public class ZhiHuStoryPresenter implements ZhiHuStoryContract.Presenter{

    ZhiHuStoryContract.View  mView;
    ApiService mService;

    @Inject
    ZhiHuStoryPresenter(ZhiHuStoryContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }


    @Override
    public void loadZhiHuStoryInfo(int storyID) {
        mService.getStory(storyID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Story>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Story story) {
                        mView.onNext(story);
                    }
                });
    }
}
