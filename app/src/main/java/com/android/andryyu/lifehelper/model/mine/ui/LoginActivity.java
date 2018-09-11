package com.android.andryyu.lifehelper.model.mine.ui;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseActivity;
import com.android.andryyu.lifehelper.common.data.Constants;
import com.android.andryyu.lifehelper.common.data.SPUtil;
import com.android.andryyu.lifehelper.common.data.account.Authenticator;
import com.android.andryyu.lifehelper.di.components.DaggerGithubLoginComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.GithubLoginModule;
import com.android.andryyu.lifehelper.model.mine.mvp.GithubLoginPresenter;
import com.android.andryyu.lifehelper.model.mine.mvp.contract.GithubLoginContract;
import com.android.andryyu.lifehelper.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yufei on 2017/10/28.
 */

public class LoginActivity extends BaseActivity implements GithubLoginContract.View {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText etPWD;
    @BindView(R.id.submit)
    Button submit;
    @Inject
    GithubLoginPresenter mPresenter;
    String accountName, password;

    private Bundle mResultBundle = null;
    private String mAuthTokenType;
    private String accountType;
    private AccountManager mAccountManager;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @Override
    public void initView() {
        mAccountManager = AccountManager.get(getBaseContext());
        Intent intent = getIntent();
        mAccountAuthenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }
        accountName = intent.getStringExtra(Authenticator.ARG_ACCOUNT_NAME);
        mAuthTokenType = intent.getStringExtra(Authenticator.ARG_AUTH_TYPE);
        accountType = intent.getStringExtra(Authenticator.ARG_ACCOUNT_TYPE);
        mAuthTokenType = Authenticator.AUTHTOKEN_TYPE_FULL_ACCESS;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountName= username.getText().toString().trim();
                password = etPWD.getText().toString().trim();
                if (accountName.isEmpty() || password.isEmpty()) {
                    ToastUtil.showLong(R.string.input_complete);
                    return;
                }
                mPresenter.createToken(accountName, password);
                MobclickAgent.onEvent(LoginActivity.this,"GithubLogin");
            }
        });
    }

    @Override
    public void initData() {
        NetComponent netComponent = BaseApplication.get(this).getNetComponent();
        DaggerGithubLoginComponent.builder()
                .netComponent(netComponent)
                .githubLoginModule(new GithubLoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void tokenCreated(String token) {
        SPUtil.getInstance().putString( Constants.Key.ACCOUNT, accountName);
        final Account account = new Account(accountName, accountType);
        if (getIntent().getBooleanExtra(Authenticator.ARG_IS_ADDING_NEW_ACCOUNT, true)) {
            mAccountManager.addAccountExplicitly(account, password, null);
            mAccountManager.setAuthToken(account, mAuthTokenType, token);
        } else {
            mAccountManager.setPassword(account, password);
        }
        //Save token created from loginActivity
        ((BaseApplication)this.getApplication()).setToken(token);
        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
        bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        bundle.putString(AccountManager.KEY_AUTHTOKEN, token);
        bundle.putString(Authenticator.PARAM_USER_PASS, password);
        setAccountAuthenticatorResult(bundle);
        setResult(RESULT_OK, new Intent().putExtras(bundle));
        finish();
    }

    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }

    @Override
    public void showOnSuccess() {

    }

    @Override
    public void showOnFailure(String error) {

    }

    @Override
    public void finish() {
        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED,
                        "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }
}
