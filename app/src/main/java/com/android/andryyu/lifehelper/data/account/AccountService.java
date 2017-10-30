package com.android.andryyu.lifehelper.data.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service {

    private Authenticator authenticator;

    public AccountService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
