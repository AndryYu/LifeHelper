package com.android.andryyu.lifehelper.mvp.view;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;

/**
 * Created by yufei on 2017/3/21.
 */

public class OwnspaceContract {

    public interface View extends BaseView<Presenter> {
        /**
         * <p>doOnTerminate</p>
         * @Description:    监听
         */
        void doOnTerminate();
    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>getListByPage</p>
         * @Description:加载OpenEyes列表
         */
        void getListByPage(int page, int model, String pageId,String deviceId,String createTime);

    }
}
