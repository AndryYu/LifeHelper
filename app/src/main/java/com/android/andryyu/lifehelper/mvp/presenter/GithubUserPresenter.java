package com.android.andryyu.lifehelper.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.data.RetrofitUtil;
import com.android.andryyu.lifehelper.data.account.GitHubAccount;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.GithubUserContract;
import com.andryyu.helper.sub.github.User;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yufei on 2017/10/30.
 */

public class GithubUserPresenter implements GithubUserContract.Presenter {

    public static final String TAG = GithubUserPresenter.class.getSimpleName();
    GithubUserContract.View mView;
    ApiService mService;
    Context mContext;
    private GitHubAccount gitHubAccount;

    @Inject
    GithubUserPresenter(Context context, GithubUserContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = RetrofitUtil.getJsonRetrofitInstance(context).create(ApiService.class);
        mContext = context;
        this.gitHubAccount = GitHubAccount.getInstance(mContext);
    }

    @Override
    public void auth() {
        mService.authUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "auth onFailure = " + e.toString());
                        mView.showOnFailure(BaseApplication.getContext().getString(R.string.fail_auth_user));
                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        Log.i(TAG, "auth onResponse");
                        if (userResponse.code() == 401) {
                            //gitHubAccount.invalidateToken(RetrofitUtil.token);
                            auth();
                        } else if (userResponse.isSuccessful()) {
                            mView.onFinish(userResponse.body());
                        }
                    }
                });
    }

    @Override
    public void userInfo(String usr) {

    }

    @Override
    public void hasFollow(String user) {

    }

    @Override
    public void follow(String user) {

    }

    @Override
    public void unFollow(String user) {

    }

    @Override
    public void starredCount(String user) {

    }
}
