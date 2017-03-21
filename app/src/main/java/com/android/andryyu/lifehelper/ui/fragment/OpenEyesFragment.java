package com.android.andryyu.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.widget.recycleView.EndLessOnScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/16.
 */

public class OpenEyesFragment extends BaseFragment {


    @BindView(R.id.rv_eyes)
    RecyclerView mRvEyes;
    @BindView(R.id.swl_eyes)
    SwipeRefreshLayout mSwlEyes;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eyes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mSwlEyes != null) {
            mSwlEyes.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mSwlEyes.setOnRefreshListener(
                    () -> mSwlEyes.postDelayed(this::load, 1000));
        }

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        if(mSwlEyes != null){
            mRvEyes.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {

                }
            });
        }


    }

    private void load(){

    }
}
