package com.android.andryyu.lifehelper.data.account;


import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.andryyu.lifehelper.BaseApplication;
import com.android.andryyu.lifehelper.ui.act.LoginActivity;
import com.andryyu.helper.sub.github.AuthError;
import com.andryyu.helper.sub.github.Github;
import com.andryyu.helper.sub.github.GithubError;
import com.andryyu.helper.sub.github.GithubImpl;
import com.andryyu.helper.sub.github.OverAuthError;

/**
 * Created by yufei on 2017/10/29.
 */

public class Authenticator extends AbstractAccountAuthenticator {

    private BaseApplication app;
    private  Context mContext;
    public final static String PARAM_USER_PASS = "USER_PASS";
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public final static String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";

    public Authenticator(Context context) {
        super(context);
        mContext = context;
        this.app = (BaseApplication) context.getApplicationContext();
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        final AccountManager am = AccountManager.get(mContext);
        String authToken = am.peekAuthToken(account, authTokenType);
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                Github github = new GithubImpl(mContext);
                try {
                    //Get token from server
                    authToken = github.createToken(account.name,password);
                } catch (GithubError githubError) {
                    githubError.printStackTrace();
                    authToken = "";
                } catch (AuthError authError) {
                    authError.printStackTrace();
                    authToken = "";
                } catch (OverAuthError overAuthError) {
                    overAuthError.printStackTrace();
                    authToken = "";
                }
            }else {
                //If u fail to get local token,check if there is a token saved in application.
                if(!app.getToken().isEmpty()){
                    final Bundle result = new Bundle();
                    result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                    result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                    result.putString(AccountManager.KEY_AUTHTOKEN, app.getToken());
                    return result;
                }
            }
        }else {
            //After generating
            app.setToken(authToken);
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }
        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.

        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
