package com.android.andryyu.lifehelper.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.di.components.DaggerNewListComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.NewListModule;
import com.android.andryyu.lifehelper.entity.item.NewsMultiItem;
import com.android.andryyu.lifehelper.entity.news.NewsInfo;
import com.android.andryyu.lifehelper.mvp.view.NewListContract;
import com.android.andryyu.lifehelper.ui.adapter.NewsMultiListAdapter;
import com.android.andryyu.lifehelper.utils.SliderHelper;
import com.android.andryyu.lifehelper.widget.recycleView.XRecyclerView;
import com.dl7.recycler.adapter.BaseQuickAdapter;
import com.dl7.recycler.adapter.BaseViewHolder;
import com.trello.rxlifecycle2.LifecycleTransformer;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yufei on 2017/10/29.
 */

public class ToutiaoFragment extends BaseFragment implements NewListContract.View{
    @BindView(R.id.rv_toutiao)
    XRecyclerView rvToutiao;
    @BindView(R.id.swl_toutiao)
    SwipeRefreshLayout swlToutiao;
    Unbinder unbinder;

    BaseQuickAdapter mAdapter;
    //private SliderLayout mAdSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toutiao, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new NewsMultiListAdapter(getActivity());
        initDagger();
    }

    private void initDagger(){
        NetComponent netComponent = BaseApplication.get(getActivity()).getNetComponent();
        DaggerNewListComponent.builder()
                .netComponent(netComponent)
                .newListModule(new NewListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void onFreshData(List<NewsMultiItem> newsMultiItems) {

    }

    @Override
    public void onLoadMoreData(List<NewsMultiItem> newsMultiItems) {

    }

    @Override
    public void onAdData(NewsInfo newsBean) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.head_news_list, null);
       /* mAdSlider = (SliderLayout) view.findViewById(R.id.slider_ads);
        SliderHelper.initAdSlider(getActivity(), mAdSlider, newsBean);
        mAdapter.addHeaderView(view);*/
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return null;
    }

}
