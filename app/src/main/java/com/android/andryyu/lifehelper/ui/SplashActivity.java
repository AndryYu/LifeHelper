package com.android.andryyu.lifehelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.ui.act.MainActivity;
import com.android.andryyu.lifehelper.widget.particleview.ParticleView;
import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends AppCompatActivity {

    private ParticleView pv_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MobclickAgent.openActivityDurationTrack(false);
        pv_logo = findViewById(R.id.pv_logo);
        pv_logo.setOnParticleAnimListener(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        });
        pv_logo.startAnim();
    }
}
