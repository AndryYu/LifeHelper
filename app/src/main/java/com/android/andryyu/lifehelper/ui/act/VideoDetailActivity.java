package com.android.andryyu.lifehelper.ui.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.utils.ImageUtil;
import com.android.andryyu.lifehelper.utils.NetUtil;
import com.android.andryyu.lifehelper.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频详情activity
 */
public class VideoDetailActivity extends AppCompatActivity {

    @BindView(R.id.video_detail_iv)
    ImageView videoDetailIv;
    @BindView(R.id.video_paly)
    ImageView videoPaly;
    @BindView(R.id.video_detail_ivmo)
    ImageView videoDetailIvmo;
    @BindView(R.id.video_detail_title)
    TextView videoDetailTitle;
    @BindView(R.id.video_detail_time)
    TextView videoDetailTime;
    @BindView(R.id.video_detail_desc)
    TextView videoDetailDesc;

    private String video;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        initData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //初始化数据
    private void initData() {
        String feed = getIntent().getStringExtra("feed");//背景图片
        title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");//时间
        String desc = getIntent().getStringExtra("desc");//视频详情
        String blurred = getIntent().getStringExtra("blurred");//模糊图片
        video = getIntent().getStringExtra("video");//视频播放地址
        //给控件设置数据
       // videoDetailIv.setImageURI(Uri.parse(feed));
        ImageUtil.loadUrl(this, Uri.parse(feed), videoDetailIv);
        videoDetailTitle.setText(title);
        videoDetailTime.setText(time);
        videoDetailDesc.setText(desc);
        //videoDetailIvmo.setImageURI(Uri.parse(blurred));
        ImageUtil.loadUrl(this, Uri.parse(blurred), videoDetailIvmo);
    }


    @OnClick({R.id.video_paly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_paly://播放
                if (NetUtil.isNetworkReachable(this)) {
                    Intent intent=new Intent(this,ShowVideoActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("video",video);
                    bundle.putString("title",title);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    ToastUtil.showLong("网络异常，请稍后再试");
                }
                break;
        }
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
