package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.Weather;
import com.android.andryyu.lifehelper.common.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.WeatherContract;
import com.android.andryyu.lifehelper.common.rx.RxUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yufei on 2017/3/16.
 */

public class WeatherPresenter implements WeatherContract.Presenter {

    String hefengKEY = "073e0961168744f1b0cda271c9a76fe4";
    WeatherContract.View mView;
    ApiService mService;

    @Inject
    WeatherPresenter(WeatherContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }


    @Override
    public void loadWeatherInfo(String city) {

        mView.doOnRequest();
        mService.mWeatherAPI(city, hefengKEY)
                .flatMap(weatherAPI -> {
                    String status = weatherAPI.mHeWeatherDataService30s.get(0).status;
                    if ("no more requests".equals(status)) {
                        return Observable.error(new RuntimeException("/(ㄒoㄒ)/~~,API免费次数已用完"));
                    } else if ("unknown city".equals(status)) {
                        return Observable.error(new RuntimeException(String.format("API没有%s", city)));
                    }
                    return Observable.just(weatherAPI);
                })
                .map(weatherAPI -> weatherAPI.mHeWeatherDataService30s.get(0))
                .compose(RxUtils.rxSchedulerHelper())
                .doOnError(new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.doOnError();
                    }
                }).doOnNext(new Consumer<Weather>() {

            @Override
            public void accept(Weather weather) throws Exception {
                mView.doOnNext();
            }
                }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                mView.doOnTerminate();
            }

                }).subscribe(new Observer<Weather>(){
                     @Override
                     public void onComplete() {
                        mView.onCompleted();
                     }
                    @Override
                     public void onError(Throwable e) {

                    }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
                    public void onNext(Weather weather) {
                        mView.onNext(weather);
                   }
                 });
    }
}
