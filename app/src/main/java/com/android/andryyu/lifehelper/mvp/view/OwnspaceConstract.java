package com.android.andryyu.lifehelper.mvp.view;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;

/**
 * Created by yufei on 2017/3/21.
 */

public class OwnspaceConstract {

    public interface View extends BaseView<Presenter> {

    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>loadOpenEyesInfo</p>
         * @Description:加载OpenEyes列表
         */
        void  loadOwnSpaceInfo(String city);
    }
}
