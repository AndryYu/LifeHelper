package com.android.andryyu.lifehelper.mvp.view;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.android.andryyu.lifehelper.data.entity.Weather;

/**
 * Created by yufei on 2017/3/16.
 */

public class WeatherContract {

    public interface View extends BaseView<Presenter> {
        /**
         * <p>doOnRequest</p>
         * @Description:  网络请求前
         */
        void  doOnRequest();

        /**
         * <p>doOnError</p>
         * @Description:  网络请求出错
         */
        void doOnError();

        /**
         * <p>doOnNext</p>
         * @Description:  下一步
         */
        void doOnNext();

        /**
         * <p>doOnTerminate</p>
         * @Description:    监听
         */
        void doOnTerminate();

        /**
         * <p>onCompleted</p>
         * @Description:   加载完成
         */
        void onCompleted();

        /**
         * <p>onNext</p>
         * @Description:
         */
        void onNext(Weather weather);
    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>loadWeatherInfo</p>
         * @Description:加载天气
         */
        void  loadWeatherInfo(String city);
    }
}
