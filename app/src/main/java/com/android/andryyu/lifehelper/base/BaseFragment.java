package com.android.andryyu.lifehelper.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.umeng.analytics.MobclickAgent;


public class BaseFragment extends RxFragment {

    private String Fragmentpage = "BaseFragment";

    public String getFragmentpage() {
        return Fragmentpage;
    }

    public void setFragmentpage(String fragmentpage) {
        Fragmentpage = fragmentpage;
    }

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        return fragment;
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getFragmentpage()); //统计页面，"MainScreen"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getFragmentpage());
    }
}
