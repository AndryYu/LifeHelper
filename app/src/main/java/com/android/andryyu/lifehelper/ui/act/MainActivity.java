package com.android.andryyu.lifehelper.ui.act;

import android.Manifest;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
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
import com.android.andryyu.lifehelper.ui.fragment.HomeFragment;
import com.android.andryyu.lifehelper.utils.TextUtil;
import com.android.andryyu.lifehelper.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AMapLocationListener {

    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    private BaseFragment currentFragment;

    private Toolbar mToolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FloatingActionButton mFab;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private RxPermissions mRxPermissions;
    private TextView tvLocationCity;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationClientOption mLocationOption = null;

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
        currentFragment = HomeFragment.newInstance();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fl_content, currentFragment).commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvLocationCity = (TextView) headerView.findViewById(R.id.tv_header_city);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherActivity.launch(MainActivity.this);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(permission  -> {
                    if (permission.granted) {
                        if(permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                        }else if(permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)){
                            location();
                        }
                    } else {
                        if(permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                        }else if(permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)){

                        }
                    }
                });
    }

    public void setToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            mToolbar = toolbar;
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("生活助手");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(check()) {
                super.onBackPressed();
            }else{
                Snackbar.make(drawer, "再按一次退出！", Snackbar.LENGTH_LONG).show();
            }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.nav_world_eyes) {
            currentFragment = HomeFragment.newInstance();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fl_content, currentFragment).commit();
        } else if (id == R.id.nav_life_note) {

        } else if (id == R.id.nav_music) {

        } else if (id == R.id.nav_book) {

        } else if (id == R.id.nav_ganhuo) {

        } else if (id == R.id.nav_theme) {
            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                            == Configuration.UI_MODE_NIGHT_YES) {
                        SPUtil.getInstance().setAppModule(0);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        SPUtil.getInstance().setAppModule(1);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * <p>location</p>
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
}
