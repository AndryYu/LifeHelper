package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.data.entity.BaseEntity;
import com.android.andryyu.lifehelper.data.entity.Daily;
import com.android.andryyu.lifehelper.data.entity.StorySection;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.ZhiHuContract;
import com.android.andryyu.lifehelper.rx.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

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
        observable.compose(RxUtils.rxSchedulerHelper())
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
