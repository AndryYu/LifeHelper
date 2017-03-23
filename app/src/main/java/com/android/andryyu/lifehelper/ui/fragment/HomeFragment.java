package com.android.andryyu.lifehelper.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.ui.act.MainActivity;
import com.android.andryyu.lifehelper.ui.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/17.
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.tab_home)
    TabLayout mTabHome;
    @BindView(R.id.vp_home)
    ViewPager mVpHome;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab_home)
    FloatingActionButton mFabHome;
    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleList.add("开眼视频");
       // mTitleList.add("单读");
        mTitleList.add("知乎日报");
        mFragments.add(new OpenEyesFragment());
        //mFragments.add(new OwnspaceFragment());
        mFragments.add(new ZhiHuFragment());
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mVpHome.setAdapter(myAdapter);
        mVpHome.setOffscreenPageLimit(1);
        myAdapter.notifyDataSetChanged();

        mTabHome.setTabMode(TabLayout.MODE_FIXED);
        mTabHome.setupWithViewPager(mVpHome);
        ((MainActivity) getActivity()).setToolbar(mToolbar);
        ((MainActivity) getActivity()).setFab(mFabHome);
    }

}
