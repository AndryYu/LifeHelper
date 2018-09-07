package com.android.andryyu.lifehelper.ui.act;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.andryyu.lifehelper.R;


/**
 * 视频播放界面
 */
public class ShowVideoActivity extends AppCompatActivity {

    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = ShowVideoActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        setContentView(R.layout.activity_show_video);
        initView();

    }

    //初始化控件
    private void initView() {

    }

}
