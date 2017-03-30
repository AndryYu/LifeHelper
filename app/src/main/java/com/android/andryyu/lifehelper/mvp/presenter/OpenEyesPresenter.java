package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.data.entity.HomePicEntity;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.OpenEyesContract;
import com.android.andryyu.lifehelper.rx.RxUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by yufei on 2017/3/21.
 */

public class OpenEyesPresenter implements OpenEyesContract.Presenter{

    OpenEyesContract.View mView;
    ApiService mService;
    private Gson mGson;
    List<HomePicEntity.IssueListEntity.ItemListEntity> listAll = new ArrayList<>();

    @Inject
    OpenEyesPresenter(OpenEyesContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
        mGson = new Gson();
    }

    @Override
    public void loadOpenEyesInfo(String url) {
        Observable<HomePicEntity> observable = mService.OpenEyesVideo(url);
        observable.compose(RxUtils.rxSchedulerHelper())
                .doOnRequest(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.doOnRequest();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mView.doOnTerminate();
                    }
                })
                .subscribe(new Observer<HomePicEntity>(){

                    @Override
                    public void onNext(HomePicEntity entity) {
                        List<HomePicEntity.IssueListEntity> issueList = entity.getIssueList();
                        HomePicEntity.IssueListEntity issueListEntity = issueList.get(0);
                        List<HomePicEntity.IssueListEntity.ItemListEntity> itemList = issueListEntity.getItemList();
                        HomePicEntity.IssueListEntity issueListEntity2 = issueList.get(1);
                        List<HomePicEntity.IssueListEntity.ItemListEntity> itemList1 = issueListEntity2.getItemList();

                        listAll.addAll(itemList);
                        listAll.addAll(itemList1);
                        mView.onNextPagerUrl(entity.getNextPageUrl());
                        mView.onNotify(listAll);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }
                });
    }
}
