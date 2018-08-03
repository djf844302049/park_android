package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/2/27.
 */

public class WebViewActivity extends BaseActivity {
    private LinearLayout llRoot;
    private ProgressBar pb;
    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    public static void actionStart(Context context, String path) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }

    public static void actionStartForResult(BaseActivity context, String path, int requestCode) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("path", path);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initData() {
        webView = new WebView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(layoutParams);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        llRoot = findViewById(R.id.ll_webview_root);
        pb = findViewById(R.id.pb_webview);
        llRoot.addView(webView);
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);//将图片调整到合适大小
        settings.setLoadWithOverviewMode(true);//缩放至屏幕大小
        settings.setSupportZoom(false);//取消缩放
        settings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress != 100) {
                    pb.setVisibility(View.VISIBLE);
                    pb.setProgress(newProgress);
                } else {
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    initTitle(title);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }
        });
        webView.requestFocus();
        String path = getIntent().getStringExtra("path");
        webView.loadUrl(path);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        llRoot.removeAllViews();
        webView.clearHistory();
        webView.removeAllViews();
        webView.destroy();
        super.onDestroy();
    }
}
