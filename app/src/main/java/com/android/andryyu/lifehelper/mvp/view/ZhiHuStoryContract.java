package com.android.andryyu.lifehelper.mvp.view;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.android.andryyu.lifehelper.entity.zhihu.Story;

/**
 * Created by yufei on 2017/3/22.
 */

public class ZhiHuStoryContract {

    public interface View extends BaseView<Presenter> {

        /**
         * <p>onNext</p>
         * @param data
         * @Description:    下一步
         */
        void  onNext(Story data);

        /**
         * <p>doOnTerminate</p>
         * @Description:    监听
         */
        void doOnTerminate();

        /**
         * <p>onError</p>
         * @param e
         */
        void onError(Throwable e);
    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>loadOpenEyesInfo</p>
         * @Description:加载OpenEyes列表
         */
        void  loadZhiHuStoryInfo(int storyID);
    }
}
