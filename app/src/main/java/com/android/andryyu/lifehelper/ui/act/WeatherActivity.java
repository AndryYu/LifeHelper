package com.android.andryyu.lifehelper.ui.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseActivity;
import com.android.andryyu.lifehelper.data.SPUtil;
import com.android.andryyu.lifehelper.data.entity.Weather;
import com.android.andryyu.lifehelper.di.components.DaggerWeatherComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.WeatherModule;
import com.android.andryyu.lifehelper.mvp.presenter.WeatherPresenter;
import com.android.andryyu.lifehelper.mvp.view.WeatherContract;
import com.android.andryyu.lifehelper.ui.adapter.WeatherAdapter;
import com.android.andryyu.lifehelper.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/16.
 */

public class WeatherActivity extends BaseActivity implements WeatherContract.View {

    @BindView(R.id.rv_weather)
    RecyclerView mRecyclerView;
    @BindView(R.id.swl_weather)
    SwipeRefreshLayout mSwiprefresh;

    private static Weather mWeather = new Weather();
    private WeatherAdapter mAdapter;

    @Inject
    WeatherPresenter mWeatherPresenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, WeatherActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @Override
    public void initView() {
        if (mSwiprefresh != null) {
            mSwiprefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mSwiprefresh.setOnRefreshListener(
                    () -> mSwiprefresh.postDelayed(this::load, 1000));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WeatherAdapter(mWeather);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        NetComponent netComponent = BaseApplication.get(this).getNetComponent();
        DaggerWeatherComponent.builder()
                .netComponent(netComponent)
                .weatherModule(new WeatherModule(this))
                .build()
                .inject(this);

        load();
    }

    private void load() {
        String cityName = SPUtil.getInstance().getCityName();
        getSupportActionBar().setTitle(cityName);
        mWeatherPresenter.loadWeatherInfo(cityName);
    }

    @Override
    public void doOnRequest() {
        mSwiprefresh.setRefreshing(true);
    }

    @Override
    public void doOnError() {
        mRecyclerView.setVisibility(View.GONE);
        SPUtil.getInstance().setCityName("北京");
    }

    @Override
    public void doOnNext() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void doOnTerminate() {
        mSwiprefresh.setRefreshing(false);
    }

    @Override
    public void onCompleted() {
        ToastUtil.showShort(getString(R.string.weather_complete));
    }

    @Override
    public void onNext(Weather weather) {
        mWeather.status = weather.status;
        mWeather.aqi = weather.aqi;
        mWeather.basic = weather.basic;
        mWeather.suggestion = weather.suggestion;
        mWeather.now = weather.now;
        mWeather.dailyForecast = weather.dailyForecast;
        mWeather.hourlyForecast = weather.hourlyForecast;
        //mActivity.getToolbar().setTitle(weather.basic.city);
        //safeSetTitle(weather.basic.city);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
