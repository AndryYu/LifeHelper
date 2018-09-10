package com.android.andryyu.lifehelper.model.havefun;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.android.andryyu.lifehelper.R;
import com.android.andryyu.lifehelper.base.BaseActivity;
import com.android.andryyu.lifehelper.utils.ImageUtil;
import com.android.andryyu.lifehelper.utils.NetUtil;
import com.android.andryyu.lifehelper.utils.ToastUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频详情activity
 */
public class VideoDetailActivity extends BaseActivity {

    @BindView(R.id.video_detail_ivmo)
    ImageView videoDetailIvmo;
    @BindView(R.id.video_detail_title)
    TextView videoDetailTitle;
    @BindView(R.id.video_detail_time)
    TextView videoDetailTime;
    @BindView(R.id.video_detail_desc)
    TextView videoDetailDesc;
    @BindView(R.id.mSurfaceView)
    SurfaceView mSurfaceView;

    private String video;
    private String title;
    AliVcMediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);

        initView();
        initData();
        initPlayer();
    }

    @Override
    public void initView() {
        String feed = getIntent().getStringExtra("feed");//背景图片
        title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");//时间
        String desc = getIntent().getStringExtra("desc");//视频详情
        String blurred = getIntent().getStringExtra("blurred");//模糊图片
        video = getIntent().getStringExtra("video");//视频播放地址
        videoDetailTitle.setText(title);
        videoDetailTime.setText(time);
        videoDetailDesc.setText(desc);
        ImageUtil.loadUrlWithZoom(this, Uri.parse(blurred), videoDetailIvmo);

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                // holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(holder.getSurface());
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                if (mPlayer != null) {
                    mPlayer.setSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    //初始化数据
    public void initData() {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPlayer() {
        mPlayer = new AliVcMediaPlayer(this, mSurfaceView);
        //开启循环播放
        mPlayer.setCirclePlay(true);

        mPlayer.setPreparedListener(new MyPrepareListener(this));
        mPlayer.setPcmDataListener(new MyPcmDataListener(this));
        mPlayer.setCircleStartListener(new MyCircleStartListener(this));
        mPlayer.setFrameInfoListener(new MyFrameInfoListener(this));
        mPlayer.setErrorListener(new MyErrorListener(this));
        mPlayer.setCompletedListener(new MyCompletedListener(this));
        mPlayer.setSeekCompleteListener(new MySeekCompleteListener(this));
        mPlayer.setStoppedListener(new MyPlayerStoppedListener(this));
        mPlayer.setRefer("http://aliyun.com");
        //打开、关闭播放器日志
        mPlayer.enableNativeLog();
    }


    @OnClick({R.id.video_paly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_paly://播放
                if (NetUtil.isNetworkReachable(this)) {
                    if (mPlayer != null) {
                        mPlayer.prepareToPlay(video);
                    }
                } else {
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

    private static class MyPrepareListener implements MediaPlayer.MediaPlayerPreparedListener {
        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MyPrepareListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }

        @Override
        public void onPrepared() {
            VideoDetailActivity VideoDetailActivity = VideoDetailActivityWeakReference.get();
            if (VideoDetailActivity != null) {
                VideoDetailActivity.onPrepared();
            }
        }
    }

    private void onPrepared() {
        mPlayer.play();
    }

    private static class MyPcmDataListener implements MediaPlayer.MediaPlayerPcmDataListener {

        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MyPcmDataListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }


        @Override
        public void onPcmData(byte[] bytes, int i) {

        }
    }


    private static class MyCircleStartListener implements MediaPlayer.MediaPlayerCircleStartListener {
        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MyCircleStartListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }

        @Override
        public void onCircleStart() {

        }
    }


    private static class MyErrorListener implements MediaPlayer.MediaPlayerErrorListener {

        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MyErrorListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }


        @Override
        public void onError(int i, String msg) {
            VideoDetailActivity VideoDetailActivity = VideoDetailActivityWeakReference.get();
            if (VideoDetailActivity != null) {
                VideoDetailActivity.onError(i, msg);
            }
        }
    }

    private void onError(int i, String msg) {
        if(mPlayer != null)
            mPlayer.pause();
    }

    private static class MyCompletedListener implements MediaPlayer.MediaPlayerCompletedListener {

        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MyCompletedListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }

        @Override
        public void onCompleted() {

        }
    }


    private static class MySeekCompleteListener implements MediaPlayer.MediaPlayerSeekCompleteListener {


        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MySeekCompleteListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }

        @Override
        public void onSeekCompleted() {
           
        }
    }
    

    private static class MyPlayerStoppedListener implements MediaPlayer.MediaPlayerStoppedListener {

        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MyPlayerStoppedListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }

        @Override
        public void onStopped() {
           
        }
    }
    

    private static class MyFrameInfoListener implements MediaPlayer.MediaPlayerFrameInfoListener {

        private WeakReference<VideoDetailActivity> VideoDetailActivityWeakReference;

        public MyFrameInfoListener(VideoDetailActivity VideoDetailActivity) {
            VideoDetailActivityWeakReference = new WeakReference<VideoDetailActivity>(VideoDetailActivity);
        }

        @Override
        public void onFrameInfoListener() {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.destroy();
    }
}
