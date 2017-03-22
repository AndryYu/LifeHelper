package com.android.andryyu.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.di.components.DaggerOwnspaceComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.OwnspaceModule;
import com.android.andryyu.lifehelper.mvp.presenter.OwnspacePresenter;
import com.android.andryyu.lifehelper.mvp.view.OwnspaceContract;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/16.
 */

public class OwnspaceFragment extends BaseFragment implements OwnspaceContract.View{

    @Inject
    OwnspacePresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDagger();


    }

    private  void initDagger(){
        NetComponent netComponent = BaseApplication.get(getActivity()).getNetComponent();
        DaggerOwnspaceComponent.builder()
                .netComponent(netComponent)
                .ownspaceModule(new OwnspaceModule(this))
                .build()
                .inject(this);
    }

    /**
     * <p>loadData</p>
     * @param page
     * @param mode
     * @param pageId
     * @param createTime
     */
    private void loadData(int page, int mode, String pageId, String createTime) {

    }

    @Override
    public void doOnTerminate() {

    }
}
