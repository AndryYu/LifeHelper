package com.android.andryyu.lifehelper.model.mine.mvp.contract;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.andryyu.helper.sub.github.User;

/**
 * Created by yufei on 2017/10/30.
 */

public class GithubUserContract {
    public interface View extends BaseView<GithubLoginContract.Presenter> {

        void doneAuth(User user);

        void onFinish(User user);
        void updateFollowState(boolean isFollow);
        void loadStarredCount(int count);

        void showOnFailure(String error);
    }

    public interface Presenter extends BasePresenter {
        void auth();
        void userInfo(String usr);
        void hasFollow(String user);
        void follow(String user);
        void unFollow(String user);
        void starredCount(String user);
    }
}
