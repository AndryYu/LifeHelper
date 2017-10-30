package com.android.andryyu.lifehelper.mvp.presenter;

import android.util.Log;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.data.Constants;
import com.android.andryyu.lifehelper.http.api.ApiService;
import com.android.andryyu.lifehelper.mvp.view.GithubLoginContract;
import com.andryyu.helper.sub.github.Empty;
import com.andryyu.helper.sub.github.GithubToken;
import com.andryyu.helper.sub.http.Base64;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yufei on 2017/10/30.
 */

public class GithubLoginPresenter implements GithubLoginContract.Presenter {

    public static final String TAG = GithubLoginPresenter.class.getSimpleName();
    GithubLoginContract.View mView;
    ApiService mService;

    @Inject
    GithubLoginPresenter(GithubLoginContract.View tasksView, ApiService service) {
        mView = tasksView;
        this.mService = service;
    }

    @Override
    public void createToken(String name, String password) {
        final GithubToken token = new GithubToken();
        token.setNote(Constants.TOKEN_NOTE);
        token.setScopes(Arrays.asList(Constants.SCOPES));

        mService.createToken(token,"Basic " + Base64.encode(name + ':' + password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<GithubToken>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showOnFailure(BaseApplication.getContext().getResources().getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(Response<GithubToken> tokenResponse) {
                        if(tokenResponse.isSuccessful()){
                            Log.i(TAG, "Token created sucessfully-(new)");
                            mView.tokenCreated(tokenResponse.body().getToken());
                        }else if(tokenResponse.code() == 401){
                            Log.i(TAG,"Token created fail: username or password is incorrect");
                            mView.showOnFailure(BaseApplication.getContext().getResources().getString(R.string.auth_error));
                        }else if(tokenResponse.code() == 403){
                            Log.i(TAG,"Token created fail: auth over-try");
                            mView.showOnFailure(BaseApplication.getContext().getResources().getString(R.string.over_auth_error));
                        }else if(tokenResponse.code() == 422){
                            Log.i(TAG,"Token created fail: try to delete existing token");
                            findCertainTokenID(name,password);
                        }
                    }
                });
    }

    public void findCertainTokenID(final String username, final String password){
        Log.i(TAG,"Find certain token in existing tokens");
        mService.listToken("Basic " + Base64.encode(username + ':' + password))
                .flatMap(new Func1<Response<List<GithubToken>>, Observable<Response<Empty>>>() {
                    @Override
                    public Observable<Response<Empty>> call(Response<List<GithubToken>> listResponse) {
                        for(GithubToken token : listResponse.body()){
                            Log.i(TAG,"Find certain token in existing tokens : " +token.getNote() );
                            if(Constants.TOKEN_NOTE.equals(token.getNote())){
                                return mService.removeToken("Basic " + Base64.encode(username + ':' + password), String.valueOf(token.getId()));
                            }
                        }
                        return Observable.empty();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Empty>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showOnFailure(BaseApplication.getContext().getResources().getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(Response<Empty> listResponse) {
                        if(listResponse.code() == 204){
                            Log.i(TAG,"Deteled token successfully");
                            Log.i(TAG,"Try to get an entirely new token");
                            createToken(username, password);
                        }else{
                            mView.showOnFailure(BaseApplication.getContext().getResources().getString(R.string.network_error));
                        }
                    }
                });
    }
}
