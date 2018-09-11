package com.android.andryyu.lifehelper.model.ditowine.mvp;

import com.android.andryyu.lifehelper.entity.douban.HotMovieBean;
import com.android.andryyu.lifehelper.common.http.api.ApiService;
import com.android.andryyu.lifehelper.model.ditowine.mvp.contract.MovieListContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showOnFailure(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        mView.refreshView(hotMovieBean);
                    }
                });
    }
}
