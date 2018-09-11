package com.android.andryyu.lifehelper.model.ditowine.mvp;

import com.android.andryyu.lifehelper.entity.zhihu.BaseEntity;
import com.android.andryyu.lifehelper.entity.zhihu.Daily;
import com.android.andryyu.lifehelper.entity.zhihu.StorySection;
import com.android.andryyu.lifehelper.common.http.api.ApiService;
import com.android.andryyu.lifehelper.model.ditowine.mvp.contract.ZhiHuContract;
import com.android.andryyu.lifehelper.common.rx.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;


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
        mView.doOnRequest();
        Observable<Daily> observable = lastDatetime > 0 ? mService.getBefore(lastDatetime) : mService.getLatest();
        observable.compose(RxUtils.rxSchedulerHelper())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.doOnTerminate();
                    }
                })
                .subscribe(new Observer<Daily>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

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
                    public void onComplete() {

                    }
                });
    }
}
