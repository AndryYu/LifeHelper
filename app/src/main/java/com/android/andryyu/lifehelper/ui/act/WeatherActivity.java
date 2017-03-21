package com.android.andryyu.lifehelper.ui.act;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseActivity;
import com.android.andryyu.lifehelper.data.SharedPreferenceUtil;
import com.android.andryyu.lifehelper.data.entity.Weather;
import com.android.andryyu.lifehelper.di.components.DaggerWeatherComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.WeatherModule;
import com.android.andryyu.lifehelper.mvp.presenter.WeatherPresenter;
import com.android.andryyu.lifehelper.mvp.view.WeatherContract;
import com.android.andryyu.lifehelper.rx.pm.RxPermissions;
import com.android.andryyu.lifehelper.ui.adapter.WeatherAdapter;
import com.android.andryyu.lifehelper.utils.TextUtil;
import com.android.andryyu.lifehelper.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/16.
 */

public class WeatherActivity extends BaseActivity implements AMapLocationListener, WeatherContract.View {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationClientOption mLocationOption = null;

    @BindView(R.id.rv_weather)
    RecyclerView mRecyclerView;
    @BindView(R.id.swl_weather)
    SwipeRefreshLayout mSwiprefresh;

    private static Weather mWeather = new Weather();
    private WeatherAdapter mAdapter;

    private RxPermissions mRxPermissions;

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
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                          location();
                    } else {
                         load();
                    }
                });
    }

    @Override
    public void initData() {
        NetComponent netComponent = BaseApplication.get(this).getNetComponent();
        DaggerWeatherComponent.builder()
                .netComponent(netComponent)
                .weatherModule(new WeatherModule(this))
                .build()
                .inject(this);
    }

    /**
     * 高德定位
     */
    private void location() {
        //mRefreshLayout.setRefreshing(true);
        //初始化定位
        mLocationClient = new AMapLocationClient(BaseApplication.getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔 单位毫秒
        int tempTime = SharedPreferenceUtil.getInstance().getAutoUpdate();
        if (tempTime == 0) {
            tempTime = 100;
        }
        mLocationOption.setInterval(tempTime * SharedPreferenceUtil.ONE_HOUR);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void load() {

        String cityName =  SharedPreferenceUtil.getInstance().getCityName();
        mWeatherPresenter.loadWeatherInfo(cityName);
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null) {
            if (location.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                location.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                SharedPreferenceUtil.getInstance().setCityName(TextUtil.replaceCity(location.getCity()));
            } else {
                ToastUtil.showShort(getString(R.string.weather_errorLocation));
            }
            load();
        }
    }

    @Override
    public void doOnRequest() {
        mSwiprefresh.setRefreshing(true);
    }

    @Override
    public void doOnError() {
        mRecyclerView.setVisibility(View.GONE);
        SharedPreferenceUtil.getInstance().setCityName("北京");
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
