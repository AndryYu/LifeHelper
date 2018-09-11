package com.android.andryyu.lifehelper.model.havefun.mvp.contract;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.android.andryyu.lifehelper.entity.HomePicEntity;

import java.util.List;

/**
 * Created by yufei on 2017/3/21.
 */

public class OpenEyesContract {

    public interface View extends BaseView<Presenter> {

        /**
         * <p>doOnRequest</p>
         * @Description:  网络请求前
         */
        void  doOnRequest();

        /**
         * <p>doOnTerminate</p>
         * @Description:    监听
         */
        void doOnTerminate();

        void onNextPagerUrl(String url);

        void onNotify(List<HomePicEntity.IssueListEntity.ItemListEntity> lists);

        void onError(Throwable e);
    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>loadOpenEyesInfo</p>
         * @Description:加载OpenEyes列表
         */
        void  loadOpenEyesInfo(String url);
    }
}
