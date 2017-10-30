package com.android.andryyu.lifehelper.mvp.view;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.android.andryyu.lifehelper.entity.dandu.DetailEntity;

/**
 * Created by yufei on 2017/10/29.
 */

public class ArtDetailContract {
    public interface Presenter extends BasePresenter {
        void getDetail(String itemId);
    }

    public interface View extends BaseView<ArtDetailContract.Presenter> {
        void updateListUI(DetailEntity detailEntity);
        void showOnFailure();
    }
}
