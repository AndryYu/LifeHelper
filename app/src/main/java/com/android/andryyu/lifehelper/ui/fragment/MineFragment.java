package com.android.andryyu.lifehelper.ui.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseFragment;
import com.android.andryyu.lifehelper.data.Constants;
import com.android.andryyu.lifehelper.data.SPUtil;
import com.android.andryyu.lifehelper.di.components.DaggerGithubUserComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.GithubUserModule;
import com.android.andryyu.lifehelper.mvp.presenter.GithubUserPresenter;
import com.android.andryyu.lifehelper.mvp.view.GithubUserContract;
import com.android.andryyu.lifehelper.utils.ImageUtil;
import com.android.andryyu.lifehelper.widget.UserLabel;
import com.andryyu.helper.sub.github.User;
import com.bumptech.glide.Glide;
import com.github.quinn.iconlibrary.icons.OctIcon;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yufei on 2017/10/28.
 */

public class MineFragment extends BaseFragment implements GithubUserContract.View {

    private String TAG = MineFragment.class.getSimpleName();
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.backdrop)
    ImageView backDrop;

    @BindView(R.id.starLabel)
    UserLabel starLabel;
    @BindView(R.id.followersLabel)
    UserLabel followersLabel;
    @BindView(R.id.followingsLabel)
    UserLabel followingsLabel;


    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.scrollWrap)
    View scrollWrap;

    @BindView(R.id.emailLayout)
    View emailLayout;
    @BindView(R.id.blogLayout)
    View blogLayout;
    @BindView(R.id.companyLayout)
    View companyLayout;
    @BindView(R.id.locationLayout)
    View locationLayout;
    @BindView(R.id.joinLayout)
    View joinLayout;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private User user;
    @Inject
    GithubUserPresenter mPresenter;


    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    public static class IconKeyValueViewHolder {
        @BindView(R.id.textIcon)
        public ImageView icon;
        @BindView(R.id.textKey)
        public TextView textKey;
        @BindView(R.id.textValue)
        public TextView textValue;
    }

    IconKeyValueViewHolder emailHolder = new IconKeyValueViewHolder();
    IconKeyValueViewHolder blogHolder = new IconKeyValueViewHolder();
    IconKeyValueViewHolder companyHolder = new IconKeyValueViewHolder();
    IconKeyValueViewHolder joinHolder = new IconKeyValueViewHolder();
    IconKeyValueViewHolder locationHolder = new IconKeyValueViewHolder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);

        ButterKnife.bind(joinHolder, joinLayout);
        ButterKnife.bind(emailHolder, emailLayout);
        ButterKnife.bind(companyHolder, companyLayout);
        ButterKnife.bind(blogHolder, blogLayout);
        ButterKnife.bind(locationHolder, locationLayout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFragmentpage(TAG);
        initView();
        initDagger();
        mPresenter.auth();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        String account = SPUtil.getInstance().getString(Constants.Key.ACCOUNT);
        if(TextUtils.isEmpty(account)){
            account = getActivity().getString(R.string.title_notifications);
        }
        toolbar.setTitle(account);
    }

    private void initView() {
        ImageUtil.setIconFont(getActivity(), emailHolder.icon, OctIcon.EMAIL, R.color.colorPrimary);
        ImageUtil.setIconFont(getActivity(), blogHolder.icon, OctIcon.BLOG, R.color.colorPrimary);
        ImageUtil.setIconFont(getActivity(), companyHolder.icon, OctIcon.COMPANY, R.color.colorPrimary);
        ImageUtil.setIconFont(getActivity(), locationHolder.icon, OctIcon.LOCATE, R.color.colorPrimary);
        ImageUtil.setIconFont(getActivity(), joinHolder.icon, OctIcon.JOIN, R.color.colorPrimary);

        emailHolder.textKey.setText(R.string.email);
        blogHolder.textKey.setText(R.string.blog);
        companyHolder.textKey.setText(R.string.company);
        joinHolder.textKey.setText(R.string.join);
        locationHolder.textKey.setText(R.string.location);

        emailHolder.textValue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        emailHolder.textValue.getPaint().setAntiAlias(true);//抗锯齿
        blogHolder.textValue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        blogHolder.textValue.getPaint().setAntiAlias(true);//抗锯齿
    }

    private void initDagger() {
        NetComponent netComponent = BaseApplication.get(getActivity()).getNetComponent();
        DaggerGithubUserComponent.builder()
                .netComponent(netComponent)
                .githubUserModule(new GithubUserModule(getActivity(), this))
                .build()
                .inject(this);
    }

    @Override
    public void doneAuth(User user) {
        this.user = user;
    }

    @Override
    public void onFinish(User user) {
        this.user = user;
        loadUser(user);
        Glide.with(getActivity()).load(user.getAvatar_url()).centerCrop().into(backDrop);
    }

    public void loadUser(User user) {
        if (user == null)
            return;
        starLabel.setValue("" + user.getPublic_repos());
        followersLabel.setValue("" + user.getFollowers());
        followingsLabel.setValue("" + user.getFollowing());

        //名字
        if (TextUtils.isEmpty(user.getName())) {
            nickname.setText(this.user.getLogin());
        } else {
            nickname.setText(user.getName());
        }

        //email
        if (TextUtils.isEmpty(user.getEmail())) {
            emailLayout.setVisibility(View.GONE);
        } else {
            emailHolder.textValue.setText("" + user.getEmail());
        }

        //公司
        if (TextUtils.isEmpty(user.getCompany())) {
            companyLayout.setVisibility(View.GONE);
        } else {
            companyHolder.textValue.setText("" + user.getCompany());
        }

        //博客
        if (TextUtils.isEmpty(user.getBlog())) {
            blogLayout.setVisibility(View.GONE);
        } else {
            try {
                blogHolder.textValue.setText(user.getBlog().substring(0, 30) + "...");
            } catch (StringIndexOutOfBoundsException e) {
                blogHolder.textValue.setText("" + user.getBlog());
            }
        }

        //位置
        if (TextUtils.isEmpty(user.getLocation())) {
            locationLayout.setVisibility(View.GONE);
        } else {
            try {
                locationHolder.textValue.setText(user.getLocation().substring(0, 27) + "...");
            } catch (StringIndexOutOfBoundsException e) {
                locationHolder.textValue.setText("" + user.getLocation());
            }
        }
        //github加入时间
        Date date = user.getCreated_at();
        joinHolder.textValue.setText(date.toLocaleString());
    }

    @Override
    public void updateFollowState(boolean isFollow) {

    }

    @Override
    public void loadStarredCount(int count) {

    }

    @Override
    public void showOnFailure(String error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
