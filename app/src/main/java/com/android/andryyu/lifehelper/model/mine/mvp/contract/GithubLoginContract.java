package com.android.andryyu.lifehelper.model.mine.mvp.contract;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;

/**
 * Created by yufei on 2017/10/30.
 */

public class GithubLoginContract {
    public interface View extends BaseView<GithubLoginContract.Presenter> {

        void tokenCreated(String token);

        void showOnSuccess();

        void showOnFailure(String error);
    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>loadOpenEyesInfo</p>
         * @Description:加载OpenEyes列表
         */
        void  createToken(String name, String password);
    }
}
