package com.android.andryyu.lifehelper.ui.fragment.dandu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.di.components.DaggerOwnspaceComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.OwnspaceModule;
import com.android.andryyu.lifehelper.entity.dandu.Item;
import com.android.andryyu.lifehelper.mvp.presenter.OwnspacePresenter;
import com.android.andryyu.lifehelper.mvp.view.OwnspaceContract;
import com.android.andryyu.lifehelper.ui.adapter.VerticalPagerAdapter;
import com.android.andryyu.lifehelper.utils.AppUtil;
import com.android.andryyu.lifehelper.widget.VerticalViewPager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DanduFragment extends BaseFragment implements OwnspaceContract.View {

    @Inject
    OwnspacePresenter mPresenter;
    @BindView(R.id.vp_dandu)
    VerticalViewPager vpDandu;
    Unbinder unbinder;
    private VerticalPagerAdapter pagerAdapter;

    private String deviceId;
    private boolean isLoading = true;
    private int page = 1;

    public static DanduFragment newInstance() {
        DanduFragment fragment = new DanduFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dandu, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }


    public void initView() {
        deviceId = AppUtil.getDeviceId(getActivity());
        pagerAdapter = new VerticalPagerAdapter(getActivity().getSupportFragmentManager());
        vpDandu.setAdapter(pagerAdapter);
        vpDandu.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (pagerAdapter.getCount() <= position + 2 && !isLoading) {
                    if (isLoading) {
                        Toast.makeText(getActivity(), "正在努力加载...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loadData(page, 0, pagerAdapter.getLastItemId(), pagerAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initData() {
        initDagger();
        loadData(1, 0, "0", "0");
    }

    private void initDagger() {
        NetComponent netComponent = BaseApplication.get(getActivity()).getNetComponent();
        DaggerOwnspaceComponent.builder()
                .netComponent(netComponent)
                .ownspaceModule(new OwnspaceModule(this))
                .build()
                .inject(this);
    }

    private void loadData(int page, int mode, String pageId, String createTime) {
        isLoading = true;
        mPresenter.getListByPage(page, mode, pageId, deviceId, createTime);
    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void updateListUI(List<Item> itemList) {
        isLoading = false;
        pagerAdapter.setDataList(itemList);
        page++;
    }

    @Override
    public void showOnFailure() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
