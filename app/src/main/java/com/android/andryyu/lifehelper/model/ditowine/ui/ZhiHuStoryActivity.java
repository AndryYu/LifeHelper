package com.android.andryyu.lifehelper.model.ditowine.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseActivity;
import com.android.andryyu.lifehelper.di.components.DaggerZhiHuStoryComponent;
import com.android.andryyu.lifehelper.entity.zhihu.Story;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.ZhiHuStoryModule;
import com.android.andryyu.lifehelper.model.ditowine.mvp.ZhiHuStoryPresenter;
import com.android.andryyu.lifehelper.model.ditowine.mvp.contract.ZhiHuStoryContract;
import com.android.andryyu.lifehelper.utils.ImageUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/3/22.
 */

public class ZhiHuStoryActivity extends BaseActivity implements ZhiHuStoryContract.View {

    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;
    @BindView(R.id.web_content)
    WebView mWebContent;

    @Inject
    ZhiHuStoryPresenter mPresenter;

    public static final String ARG_STORY = "ARG_STORY";
    @BindView(R.id.tv_story_title)
    TextView mTvStoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_story);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    public void initData() {
        Story story = getIntent().getParcelableExtra(ARG_STORY);
        NetComponent netComponent = BaseApplication.get(this).getNetComponent();
        DaggerZhiHuStoryComponent.builder()
                .netComponent(netComponent)
                .zhiHuStoryModule(new ZhiHuStoryModule(this))
                .build()
                .inject(this);
        mPresenter.loadZhiHuStoryInfo(story.getId());
        mCollapsingToolbar.setTitle(story.getTitle());
        mTvStoryTitle.setText(story.getTitle());
        ImageUtil.loadUrl(this, Uri.parse(story.getImages().get(0)), mIvPic);
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onNext(Story data) {
        String localData = loadDataWithCSS(data.getBody(), data.getCss().get(0));
        mWebContent.loadDataWithBaseURL(null, localData,
                "text/html", "utf-8", null);
    }

    @Override
    public void doOnTerminate() {

    }

    @Override
    public void onError(Throwable e) {

    }

    private String loadDataWithCSS(String loadData, String cssPath) {
        String header = "<html><head><link href=\"%s\" type=\"text/css\" rel=\"stylesheet\"/></head><body>";
        String footer = "</body></html>";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(header, cssPath));
        sb.append(loadData);
        sb.append(footer);
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
