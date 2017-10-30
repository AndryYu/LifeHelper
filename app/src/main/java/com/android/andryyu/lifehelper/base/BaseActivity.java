package com.android.andryyu.lifehelper.base;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.andryyu.lifehelper.widget.SystemBarTintManager;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * <p>initView</p>
     * @Description: 初始化界面
     */
    public abstract void initView();

    /**
     * <p>initData</p>
     * @Description:    初始化数据
     */
    public abstract  void initData();

    /**
     * 设置状态栏颜色
     * 也就是所谓沉浸式状态栏
     */
    @Deprecated
    public void setStatusBarColor(int color) {
        /**
         * Android4.4以上  但是抽屉有点冲突，目前就重写一个方法暂时解决4.4的问题
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }
}
