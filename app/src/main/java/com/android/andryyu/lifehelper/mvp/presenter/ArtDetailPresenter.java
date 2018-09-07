package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.dandu.DetailEntity;
import com.android.andryyu.lifehelper.entity.dandu.Result;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.ArtDetailContract;

import javax.inject.Inject;;import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yufei on 2017/10/29.
 */

public class ArtDetailPresenter implements ArtDetailContract.Presenter{

    ArtDetailContract.View mView;
    ApiService mService;

    @Inject
    ArtDetailPresenter(ArtDetailContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }

    @Override
    public void getDetail(String itemId) {
        mService.getDetail("api", "getPost", itemId, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result.Data<DetailEntity>>() {
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
                    public void onNext(Result.Data<DetailEntity> detailEntityData) {
                        mView.updateListUI(detailEntityData.getDatas());
                    }
                });
    }
}
