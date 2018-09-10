package com.android.andryyu.lifehelper.model.ditowine;

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
import com.android.andryyu.lifehelper.di.components.DaggerMovieListComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.MovieListModule;
import com.android.andryyu.lifehelper.entity.douban.HotMovieBean;
import com.android.andryyu.lifehelper.mvp.presenter.MovieListPresenter;
import com.android.andryyu.lifehelper.mvp.view.MovieListContract;
import com.android.andryyu.lifehelper.model.ditowine.adapter.MovieLatestAdapter;
import com.android.andryyu.lifehelper.widget.recycleView.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yufei on 2017/10/29.
 */

public class MovieFragment extends BaseFragment implements MovieListContract.View{

    @BindView(R.id.rv_recommend)
    XRecyclerView rvRecommend;
    @BindView(R.id.swl_recommend)
    SwipeRefreshLayout swlRecommend;
    Unbinder unbinder;

    @Inject
    MovieListPresenter mPresenter;

    private List<HotMovieBean.SubjectsBean> subjectsBeanList;
    private MovieLatestAdapter movieLatestAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subjectsBeanList = new ArrayList<>();
        movieLatestAdapter = new MovieLatestAdapter(subjectsBeanList);
        rvRecommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecommend.setAdapter(movieLatestAdapter);
        initDagger();
        mPresenter.fetchHotMovie();
    }

    private void initDagger(){
        NetComponent netComponent = BaseApplication.get(getActivity()).getNetComponent();
        DaggerMovieListComponent.builder()
                .netComponent(netComponent)
                .movieListModule(new MovieListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void refreshView(HotMovieBean data) {
        subjectsBeanList.addAll(data.getSubjects());
        movieLatestAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOnFailure(String error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
