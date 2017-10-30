package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.douban.HotMovieBean;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.MovieListContract;
import com.android.andryyu.lifehelper.mvp.view.NewListContract;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yufei on 2017/10/30.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    public static final String TAG = NewListPresenter.class.getSimpleName();
    MovieListContract.View mView;
    ApiService mService;

    @Inject
    MovieListPresenter(MovieListContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }

    @Override
    public void fetchHotMovie() {
        mService.fetchHotMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HotMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showOnFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        mView.refreshView(hotMovieBean);
                    }
                });
    }
}
