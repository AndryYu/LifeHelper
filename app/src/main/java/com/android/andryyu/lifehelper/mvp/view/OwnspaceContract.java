package com.android.andryyu.lifehelper.mvp.view;

import com.android.andryyu.lifehelper.base.BasePresenter;
import com.android.andryyu.lifehelper.base.BaseView;
import com.android.andryyu.lifehelper.entity.dandu.Item;

import java.util.List;

/**
 * Created by yufei on 2017/3/21.
 */

public class OwnspaceContract {

    public interface View extends BaseView<Presenter> {
        void showNoMore();
        void updateListUI(List<Item> itemList);
        void showOnFailure();
    }

    public interface Presenter extends BasePresenter {
        /**
         * <p>getListByPage</p>
         * @Description:加载OpenEyes列表
         */
        void getListByPage(int page, int model, String pageId,String deviceId,String createTime);

    }
}
