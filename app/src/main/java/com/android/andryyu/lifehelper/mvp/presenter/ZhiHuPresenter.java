package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.data.entity.BaseEntity;
import com.android.andryyu.lifehelper.data.entity.Daily;
import com.android.andryyu.lifehelper.data.entity.StorySection;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.ZhiHuContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yufei on 2017/3/21.
 */

public class ZhiHuPresenter implements ZhiHuContract.Presenter {


    ZhiHuContract.View mView;
    ApiService mService;
    private List<BaseEntity> data = new ArrayList<>();
    private StorySection section;
    private int lastDatetime = 0;

    @Inject
    ZhiHuPresenter(ZhiHuContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }

    @Override
    public void loadZhiHuInfo() {
        Observable<Daily> observable = lastDatetime > 0 ? mService.getBefore(lastDatetime) : mService.getLatest();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Daily>() {
                    @Override
                    public void onNext(Daily daily) {
                        if (daily != null) {
                            lastDatetime = daily.getDate();
                            data.clear();
                            //section = new StorySection(daily.getDate());
                            //data.add(section);
                            data.addAll(daily.getStories());
                            mView.onNext(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                            mView.onError(e);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
