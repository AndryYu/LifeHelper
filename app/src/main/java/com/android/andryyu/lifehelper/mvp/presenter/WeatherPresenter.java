package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.Weather;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.WeatherContract;
import com.android.andryyu.lifehelper.rx.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

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
                .doOnRequest(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.doOnRequest();
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.doOnError();
                     }
               }).doOnNext(new Action1<Weather>() {
                     @Override
                     public void call(Weather api) {
                        mView.doOnNext();
                 }
                }).doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mView.doOnTerminate();
                     }
                }).subscribe(new Subscriber<Weather>(){
                     @Override
                     public void onCompleted() {
                        mView.onCompleted();
                     }
                    @Override
                     public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(Weather weather) {
                        mView.onNext(weather);
                   }
                 });
    }
}
