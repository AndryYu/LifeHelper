package com.android.andryyu.lifehelper.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.di.components.DaggerZhiHuComponent;
import com.android.andryyu.lifehelper.entity.zhihu.BaseEntity;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.ZhiHuModule;
import com.android.andryyu.lifehelper.mvp.presenter.ZhiHuPresenter;
import com.android.andryyu.lifehelper.mvp.view.ZhiHuContract;
import com.android.andryyu.lifehelper.ui.adapter.ZhiHuAdapter;
import com.android.andryyu.lifehelper.widget.recycleView.EndLessOnScrollListener;
import com.android.andryyu.lifehelper.widget.recycleView.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/18.
 */

public class ZhiHuFragment extends BaseFragment implements ZhiHuContract.View {

    @Inject
    ZhiHuPresenter mPresenter;
    @BindView(R.id.rv_zhihu)
    XRecyclerView mRvZhihu;
    @BindView(R.id.swl_zhihu)
    SwipeRefreshLayout mSwlZhihu;

    private LinearLayoutManager mLinearLayoutManager;
    private ZhiHuAdapter mAdapter;
    private List<BaseEntity> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDagger();

        if (mSwlZhihu != null) {
            mSwlZhihu.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mSwlZhihu.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            mSwlZhihu.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    load();
                                }
                            }, 1000);
                        }
                    });
        }
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ZhiHuAdapter(mList);
        mRvZhihu.setLayoutManager(mLinearLayoutManager);
        mRvZhihu.setAdapter(mAdapter);
        load();

        mRvZhihu.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {

            @Override
            public void onLoadMore(int currentPage) {
                load();
            }
        });
    }

    private void load(){
        mPresenter.loadZhiHuInfo();
    }

    private void initDagger() {
        NetComponent netComponent = BaseApplication.get(getActivity()).getNetComponent();
        DaggerZhiHuComponent.builder()
                .netComponent(netComponent)
                .zhiHuModule(new ZhiHuModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void doOnRequest() {
        mSwlZhihu.setRefreshing(true);
    }

    @Override
    public void onNext(List<BaseEntity> data) {
        mList.addAll(data);
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void doOnTerminate() {
        mSwlZhihu.setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {

    }
}
