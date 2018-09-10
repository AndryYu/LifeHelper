package com.android.andryyu.lifehelper.common.data.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.andryyu.lifehelper.base.BaseActivity;
import com.android.andryyu.lifehelper.common.data.Constants;
import com.android.andryyu.lifehelper.common.data.SPUtil;

/**
 * GitHub account model
 */
public class GitHubAccount {

    private static final String TAG = "GitHubAccount";
    public static final String ACCOUNT_TYPE = "com.android.andryyu.lifehelper";
    private final Account account;
    private final AccountManager manager;

    private volatile static GitHubAccount instance;
    private Context context;

    // Returns singleton class instance
    public static GitHubAccount getInstance(final Context context) {
        if (instance == null) {
            synchronized (GitHubAccount.class) {
                if (instance == null) {
                    String name = SPUtil.getInstance().getString(Constants.Key.ACCOUNT);
                    if (name.isEmpty())
                        name = "NO_ACCOUNT";
                    Account account = new Account(name, GitHubAccount.ACCOUNT_TYPE);
                    instance = new GitHubAccount(account, context);
                }
            }
        }
        return instance;
    }

    public GitHubAccount(final Account account, Context context) {
        this.account = account;
        this.manager = AccountManager.get(context);
        this.context = context;
    }

    public String getUsername() {
        return account.name;
    }

    public String getPassword() {
        return manager.getPassword(account);
    }


    public String getAuthToken() {
        Log.i(TAG, "getAuthToken");
        final AccountManagerFuture<Bundle> future = manager.getAuthToken(
                account,
                ACCOUNT_TYPE,
                null,
                (BaseActivity)context,
                null,
                null);
        try {
            Bundle result = future.getResult();
            return result.getString(AccountManager.KEY_AUTHTOKEN);
        } catch (Exception e) {
            Log.e(TAG, "Auth token lookup failed", e);
            return null;
        }
    }

    public void invalidateToken(String token){
        manager.invalidateAuthToken(ACCOUNT_TYPE,token);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + account.name + ']';
    }
}
