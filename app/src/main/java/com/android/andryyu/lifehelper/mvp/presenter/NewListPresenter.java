package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.item.NewsMultiItem;
import com.android.andryyu.lifehelper.entity.news.NewsInfo;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.NewListContract;
import com.android.andryyu.lifehelper.utils.ToastUtil;
import com.android.andryyu.lifehelper.utils.UIUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yufei on 2017/10/30.
 */
public class NewListPresenter implements NewListContract.Presenter {
    public static final String TAG = NewListPresenter.class.getSimpleName();
    NewListContract.View mView;
    ApiService mService;

    private int mPage = 0;

    @Inject
    NewListPresenter(NewListContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }

    @Override
    public void getData(String mNewsId, final boolean isRefresh) {
        mService.getNewsList("headline", mNewsId, mPage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Map<String, List<NewsInfo>>, Observable<NewsInfo>>() {
                    @Override
                    public Observable<NewsInfo> call(Map<String, List<NewsInfo>> newsListMap) {
                        return Observable.from(newsListMap.get(mNewsId));
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .filter(new Func1<NewsInfo, Boolean>() {
                    @Override
                    public Boolean call(NewsInfo newsBean) {
                        if (UIUtils.isAbNews(newsBean)) {
                            mView.onAdData(newsBean);
                        }
                        return !UIUtils.isAbNews(newsBean);
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<NewsMultiItem>>() {
                    @Override
                    public void onCompleted() {
                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh) {
                            mView.finishRefresh();
                            // 可以提示对应的信息，但不更新界面
                            ToastUtil.showLong("刷新失败提示什么根据实际情况");
                        } else {
                            mView.showNetError();
                        }
                    }

                    @Override
                    public void onNext(List<NewsMultiItem> newsMultiItems) {
                         mView.onFreshData(newsMultiItems);
                        mPage++;
                    }
                });
    }

    @Override
    public void getMoreData(String mNewsId) {
        mService.getNewsList("headline", mNewsId, mPage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Map<String, List<NewsInfo>>, Observable<NewsInfo>>() {
                    @Override
                    public Observable<NewsInfo> call(Map<String, List<NewsInfo>> newsListMap) {
                        return Observable.from(newsListMap.get(mNewsId));
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<NewsMultiItem>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(List<NewsMultiItem> newsList) {
                        mView.onLoadMoreData(newsList);
                        mPage++;
                    }
                });
    }

    /**
     * 统一变换
     */
    private Observable.Transformer<NewsInfo, List<NewsMultiItem>> mTransformer = new Observable.Transformer<NewsInfo, List<NewsMultiItem>>() {
        @Override
        public Observable<List<NewsMultiItem>> call(Observable<NewsInfo> newsInfoObservable) {
            return newsInfoObservable
                    .map(new Func1<NewsInfo, NewsMultiItem>() {
                        @Override
                        public NewsMultiItem call(NewsInfo newsBean) {
                            if (UIUtils.isNewsPhotoSet(newsBean.getSkipType())) {
                                return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, newsBean);
                            }
                            return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, newsBean);
                        }
                    })
                    .toList()
                    .compose(mView.<List<NewsMultiItem>>bindToLife());
        }
    };
}
