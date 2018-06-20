package com.anyidc.cloudpark.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.utils.ToastUtil;

public class MonitorVideoActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private VideoView videoView;
    private Dialog dialog;
    private RelativeLayout rlTop;
    private Button btnTransfer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_monitor_video;
    }

    @Override
    protected void initData() {
        initTitle("监控视频");
        videoView = findViewById(R.id.video_monitor);
        rlTop = findViewById(R.id.top_view);
        videoView.setVideoURI(Uri.parse("http://hls.open.ys7.com/openlive/ddcd25544bb0407dafb1ea6f4f7ca02e.m3u8"));
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("加载中，请稍候...");
        dialog.show();
        btnTransfer = findViewById(R.id.btn_transfer);
        btnTransfer.setOnClickListener(v -> {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制横屏
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rlTop.setVisibility(View.GONE);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rlTop.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        ToastUtil.showToast("资源加载出错", Toast.LENGTH_SHORT);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        dialog.dismiss();
        btnTransfer.setVisibility(View.VISIBLE);
        mp.start();
    }

    @Override
    public void onBackPressed() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
