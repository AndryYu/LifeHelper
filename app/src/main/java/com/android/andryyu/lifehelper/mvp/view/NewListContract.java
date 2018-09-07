package com.android.andryyu.lifehelper.mvp.view;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.android.andryyu.lifehelper.entity.item.NewsMultiItem;
import com.android.andryyu.lifehelper.entity.news.NewsInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by yufei on 2017/10/30.
 */

public class NewListContract {
    public interface View extends BaseView{

        /**
         * 显示加载动画
         */
        void showLoading();

        /**
         * 隐藏加载
         */
        void hideLoading();

        /**
         * 显示网络错误，modify 对网络异常在 BaseActivity 和 BaseFragment 统一处理
         */
        void showNetError();

        /**
         * 完成刷新, 新增控制刷新
         */
        void finishRefresh();

        //刷新数据
        void onFreshData(List<NewsMultiItem> newsMultiItems);

        //加载更多
        void onLoadMoreData(List<NewsMultiItem> newsMultiItems);

        void onAdData(NewsInfo newsBean);
        /**
         * 绑定生命周期
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();
    }

    public interface Presenter extends BasePresenter{

        void getData(String mNewsId, boolean isRefresh);


        void getMoreData(String mNewsId);
    }
}
