package com.android.andryyu.lifehelper.model.ditowine.mvp.contract;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.android.andryyu.lifehelper.entity.douban.HotMovieBean;

/**
 * Created by yufei on 2017/10/30.
 */

public class MovieListContract {

    public interface View extends BaseView<MovieListContract.Presenter> {

        void refreshView(HotMovieBean data);

        void showOnFailure(String error);
    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>loadOpenEyesInfo</p>
         * @Description:加载OpenEyes列表
         */
        void fetchHotMovie();
    }
}
