package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.dandu.Item;
import com.android.andryyu.lifehelper.entity.dandu.Result;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.OwnspaceContract;
import com.android.andryyu.lifehelper.utils.TimeUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
                .subscribe(new Observer<Result.Data<List<Item>>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showOnFailure();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result.Data<List<Item>> listData) {
                        int size = listData.getDatas().size();
                        if (size > 0) {
                            mView.updateListUI(listData.getDatas());
                        } else {
                            mView.showNoMore();
                        }
                    }
                });
    }
}
