package com.android.andryyu.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.di.components.DaggerOpenEyesComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.OpenEyesModule;
import com.android.andryyu.lifehelper.entity.HomePicEntity;
import com.android.andryyu.lifehelper.mvp.presenter.OpenEyesPresenter;
import com.android.andryyu.lifehelper.mvp.view.OpenEyesContract;
import com.android.andryyu.lifehelper.ui.adapter.OpenEyesAdapter;
import com.android.andryyu.lifehelper.widget.recycleView.EndLessOnScrollListener;
import com.android.andryyu.lifehelper.widget.recycleView.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yufei on 2017/10/28.
 */

public class VideoFragment extends BaseFragment implements OpenEyesContract.View {

    private String TAG = VideoFragment.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_eyes)
    XRecyclerView mRvEyes;
    @BindView(R.id.swl_eyes)
    SwipeRefreshLayout mSwlEyes;
    @Inject
    OpenEyesPresenter mPresenter;

    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private LinearLayoutManager mLinearLayoutManager;
    private String nextURL;
    private OpenEyesAdapter mEyesAdapter;
    private List<HomePicEntity.IssueListEntity.ItemListEntity> listAll = new ArrayList<>();
    private String FirstIndex = "http://baobab.wandoujia.com/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eyes, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDagger();
        setFragmentpage(TAG);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        tvTitle.setText("开眼");
        if (mSwlEyes != null) {
            mSwlEyes.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mSwlEyes.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            mSwlEyes.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    load(FirstIndex);
                                }
                            }, 1000);
                        }
                    });
        }

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mEyesAdapter = new OpenEyesAdapter(listAll);
        mRvEyes.setLayoutManager(mLinearLayoutManager);
        mRvEyes.setAdapter(mEyesAdapter);

        load(FirstIndex);
        mRvEyes.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                boolean isRefreshing = mSwlEyes.isRefreshing();
                if (isRefreshing) {
                    mEyesAdapter.notifyItemRemoved(mEyesAdapter.getItemCount());
                    return;
                }
                load(nextURL);
            }
        });
    }

    private void initDagger() {
        NetComponent netComponent = BaseApplication.get(getActivity()).getNetComponent();
        DaggerOpenEyesComponent.builder()
                .netComponent(netComponent)
                .openEyesModule(new OpenEyesModule(this))
                .build()
                .inject(this);
    }

    private void load(String url) {
        mPresenter.loadOpenEyesInfo(url);
    }

    @Override
    public void doOnRequest() {
        mSwlEyes.setRefreshing(true);
    }

    @Override
    public void doOnTerminate() {
        mSwlEyes.setRefreshing(false);
    }

    @Override
    public void onNextPagerUrl(String url) {
        nextURL = url;
    }

    @Override
    public void onNotify(List<HomePicEntity.IssueListEntity.ItemListEntity> lists) {
        listAll.clear();
        listAll.addAll(lists);

        if (mEyesAdapter != null) {
            mEyesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Throwable e) {
        mSwlEyes.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
