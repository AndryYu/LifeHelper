package com.android.andryyu.lifehelper.mvp.presenter;

import com.android.andryyu.lifehelper.entity.item.NewsMultiItem;
import com.android.andryyu.lifehelper.entity.news.NewsInfo;
import com.android.andryyu.lifehelper.common.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.NewListContract;
import com.android.andryyu.lifehelper.utils.ToastUtil;
import com.android.andryyu.lifehelper.utils.UIUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Map<String, List<NewsInfo>>, Observable<NewsInfo>>() {
                    @Override
                    public Observable<NewsInfo> apply(Map<String, List<NewsInfo>> newsListMap) throws Exception {
                        return Observable.fromIterable(newsListMap.get(mNewsId));
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .filter(newsInfo -> {
                    if (UIUtils.isAbNews(newsInfo)) {
                        mView.onAdData(newsInfo);
                    }
                    return !UIUtils.isAbNews(newsInfo);
                })
                .compose(handleResult())
                .subscribe(new Observer<List<NewsMultiItem>>() {
                    @Override
                    public void onComplete() {
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
                    public void onSubscribe(Disposable d) {

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
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Map<String, List<NewsInfo>>, Observable<NewsInfo>>() {
                    @Override
                    public Observable<NewsInfo> apply(Map<String, List<NewsInfo>> newsListMap) {
                        return Observable.fromIterable(newsListMap.get(mNewsId));
                    }
                })
                .compose(handleResult())
                .subscribe(new Observer<List<NewsMultiItem>>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetError();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

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
   /* private ObservableTransformer<NewsInfo, List<NewsMultiItem>> mTransformer = new ObservableTransformer<NewsInfo, List<NewsMultiItem>>() {

        @Override
        public Observable<List<NewsMultiItem>> apply(Observable<NewsInfo> newsInfoObservable) {
            return newsInfoObservable
                    .map(new Function<NewsInfo, NewsMultiItem>() {
                        @Override
                        public NewsMultiItem apply(NewsInfo newsBean) {
                            if (UIUtils.isNewsPhotoSet(newsBean.getSkipType())) {
                                return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, newsBean);
                            }
                            return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, newsBean);
                        }
                    })
                    .compose(mView.<List<NewsMultiItem>>bindToLife());
        }*/

        public <T> ObservableTransformer<NewsInfo, List<NewsMultiItem>> handleResult() {
            return upstream -> {
                  upstream.map(newsInfo -> {
                      if (UIUtils.isNewsPhotoSet(newsInfo.getSkipType())) {
                          return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, newsInfo);
                      }
                      return new NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, newsInfo);
                  });
                return Observable.empty();
            };
        }

}
