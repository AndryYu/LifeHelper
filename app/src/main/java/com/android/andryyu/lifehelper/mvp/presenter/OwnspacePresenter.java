package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.data.entity.Item;
import com.android.andryyu.lifehelper.data.entity.Result;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.OwnspaceContract;
import com.android.andryyu.lifehelper.utils.TimeUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yufei on 2017/3/21.
 */

public class OwnspacePresenter implements OwnspaceContract.Presenter {

    OwnspaceContract.View mView;
    ApiService mService;

    @Inject
    OwnspacePresenter(OwnspaceContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }

    @Override
    public void getListByPage(int page, int model, String pageId, String deviceId, String createTime) {
        mService.getList("api", "getList", page, model, pageId, createTime, "android", "1.3.0", TimeUtil.getCurrentSeconds(), deviceId, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result.Data<List<Item>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result.Data<List<Item>> listData) {

                    }
                });
    }
}
