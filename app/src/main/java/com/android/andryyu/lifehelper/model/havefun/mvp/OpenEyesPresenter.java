package com.android.andryyu.lifehelper.model.havefun.mvp;

import com.android.andryyu.lifehelper.entity.HomePicEntity;
import com.android.andryyu.lifehelper.common.http.api.ApiService;
import com.android.andryyu.lifehelper.model.havefun.mvp.contract.OpenEyesContract;
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

public class OpenEyesPresenter implements OpenEyesContract.Presenter {

    OpenEyesContract.View mView;
    ApiService mService;
    List<HomePicEntity.IssueListEntity.ItemListEntity> listAll = new ArrayList<>();

    @Inject
    OpenEyesPresenter(OpenEyesContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }

    @Override
    public void loadOpenEyesInfo(String url) {
        mView.doOnRequest();
        Observable<HomePicEntity> observable = mService.OpenEyesVideo(url);
        observable.compose(RxUtils.rxSchedulerHelper())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.doOnTerminate();
                    }
                })
                .subscribe(new Observer<HomePicEntity>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomePicEntity entity) {
                        List<HomePicEntity.IssueListEntity> issueList = entity.getIssueList();

                        HomePicEntity.IssueListEntity issueListEntity = issueList.get(0);
                        List<HomePicEntity.IssueListEntity.ItemListEntity> itemList = issueListEntity.getItemList();

                        listAll.addAll(itemList);
                        mView.onNextPagerUrl(entity.getNextPageUrl());
                        mView.onNotify(listAll);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }
                });
    }
}
