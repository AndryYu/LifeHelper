package com.android.andryyu.lifehelper.ui.act;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseActivity;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.data.SPUtil;
import com.android.andryyu.lifehelper.rx.pm.RxPermissions;
import com.android.andryyu.lifehelper.ui.fragment.MineFragment;
import com.android.andryyu.lifehelper.ui.fragment.dandu.DanduFragment;
import com.android.andryyu.lifehelper.ui.fragment.dandu.ItemFragment;
import com.android.andryyu.lifehelper.ui.fragment.VideoFragment;
import com.android.andryyu.lifehelper.ui.fragment.home.HomeFragment;
import com.android.andryyu.lifehelper.utils.TextUtil;
import com.android.andryyu.lifehelper.utils.ToastUtil;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements AMapLocationListener {

    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.navigation)
    BottomNavigationView btmNv;
    private HomeFragment homeFragment;
    private VideoFragment videoFragment;
    private DanduFragment danduFragment;
    private MineFragment mineFragment;

    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    private FragmentManager fragmentManager ;
    private RxPermissions mRxPermissions;
    private TextView tvLocationCity;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationClientOption mLocationOption = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectFragment(homeFragment);
                    return true;
                case R.id.nav_video:
                    selectFragment(videoFragment);
                    return true;
                case R.id.nav_music:
                    selectFragment(danduFragment);
                    return true;
                case R.id.navigation_notifications:
                    selectFragment(mineFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @Override
    public void initView() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        initFragment();
        btmNv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(btmNv);
    }

    /**
     * <p>initFragment</p>
     * @Description 用add方法添加所有fragment
     */
    private void initFragment(){
        //如果把FragmentTransaction作为全局变量，多次commit会报java.lang.IllegalStateException:commit already called
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(homeFragment==null) {
            homeFragment = HomeFragment.newInstance();
            ft.add(R.id.fl_content, homeFragment);
        }
        if(videoFragment==null) {
            videoFragment = VideoFragment.newInstance();
            ft.add(R.id.fl_content, videoFragment);
        }
        if(danduFragment==null) {
            danduFragment = DanduFragment.newInstance();
            ft.add(R.id.fl_content, danduFragment);
        }
        if(mineFragment==null) {
            mineFragment = MineFragment.newInstance();
            ft.add(R.id.fl_content, mineFragment);
        }
        hideAllFragment(ft);
        ft.show(homeFragment).commit();
    }

    /**
     * <p>selectFragment</p>
     * @Description 显示被选中的fragment
     * @param fragment
     */
    private void selectFragment(BaseFragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideAllFragment(ft);
        ft.show(fragment);
        ft.commit();
    }

    /**
     * <p>hideAllFragment</p>
     * @Description 隐藏全部fragment
     */
    private void hideAllFragment(FragmentTransaction ft){
        if(homeFragment!=null){
            ft.hide(homeFragment);
        }
        if(videoFragment!=null){
            ft.hide(videoFragment);
        }
        if(danduFragment!=null){
            ft.hide(danduFragment);
        }
        if(mineFragment!=null){
            ft.hide(mineFragment);
        }
    }

    @Override
    public void initData() {
        if(Build.VERSION.SDK_INT>=23){
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS)
                .subscribe(permission -> {
                    if (permission.granted) {
                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        } else if (permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            location();
                        }
                    } else {
                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        } else if (permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {

                        }
                    }
                });
        }
    }

    public void setToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            mToolbar = toolbar;
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("生活助手");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setFab(FloatingActionButton fab) {
        mFab = fab;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static long mLastClick = 0L;
    private static final int THRESHOLD = 2000;

    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        mLastClick = now;
        return b;
    }

    @Override
    public void onBackPressed() {

        if (check()) {
            super.onBackPressed();
        } else {
            //Snackbar.make(drawer, "再按一次退出！", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * <p>location</p>
     *
     * @Description:高德定位
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
        int tempTime = SPUtil.getInstance().getAutoUpdate();
        if (tempTime == 0) {
            tempTime = 100;
        }
        mLocationOption.setInterval(tempTime * SPUtil.ONE_HOUR);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null) {
            if (location.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                location.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                SPUtil.getInstance().setCityName(TextUtil.replaceCity(location.getCity()));
            } else {
                ToastUtil.showShort(getString(R.string.weather_errorLocation));
            }

            tvLocationCity.setText(SPUtil.getInstance().getCityName());
        }
    }


    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

}
