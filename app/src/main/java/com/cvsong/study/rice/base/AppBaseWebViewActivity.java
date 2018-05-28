package com.cvsong.study.rice.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.cvsong.study.library.base.BaseActivity;
import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.library.util.utilcode.util.ToastUtils;
import com.cvsong.study.library.wiget.statuslayout.OnRetryListener;
import com.cvsong.study.library.wiget.statuslayout.OnShowHideViewListener;
import com.cvsong.study.library.wiget.statuslayout.StatusLayoutManager;
import com.cvsong.study.library.wiget.titleview.CustomTitleView;
import com.cvsong.study.rice.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AppBaseWebViewActivity extends BaseActivity implements IBaseView {


    private long lastClick = 0;//上次点击时间
    protected String TAG;//Log标记
    protected StatusLayoutManager statusLayoutManager;
    private LinearLayout llContent;
    private Unbinder unbinder;
    protected CustomTitleView titleView;
    protected Activity activity;
    private ProgressBar progressBar;
    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemStyle();//设置主题样式
        setContentView(R.layout.activity_app_base_web_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        TAG = this.getClass().getSimpleName();

        llContent = findViewById(R.id.ll_content);
        titleView = findViewById(R.id.title_view);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);

        initTitle();//初始化标题栏设置
//        initStatusLayout();//初始化多状态布局
        initWebView();//初始化WebView
        unbinder = ButterKnife.bind(this);//绑定黄油刀
        activity = this;
        initView(savedInstanceState, llContent);//View初始化
        loadData();//加载数据

    }


    /**
     * 设置主题样式
     */
    protected void setThemStyle() {

    }


    /**
     * 初始化标题栏设置
     */
    private void initTitle() {
        titleView.setTitleBackgroundColor(getResources().getColor(R.color.bg_blue_3fa9c4));
        titleView.setLeftSubtitleText("返回");
        titleView.setRightSubtitleText("关闭");
       titleView.setRightSubTitleClickListener(new View.OnClickListener() {//右边关闭
           @Override
           public void onClick(View v) {
               Class<? extends AppBaseWebViewActivity> clazz = AppBaseWebViewActivity.this.getClass();
               ActivityUtils.finishActivity(clazz);
           }
       });
        titleView.setLeftSubTitleClickListener(new View.OnClickListener() {//统一处理---->左边WebView回退
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

//    /**
//     * 初始化多状态布局
//     */
//    private void initStatusLayout() {
//
//        statusLayoutManager = StatusLayoutManager.newBuilder(this)
//                .contentView(bindLayout())//绑定布局文件
//                .emptyDataView(R.layout.layout_status_emptydata)
//                .errorView(R.layout.layout_status_error)
//                .loadingView(R.layout.layout_status_loading)
//                .netWorkErrorView(R.layout.layout_status_networkerror)
//                .retryViewId(R.id.button_retry)
//                .onShowHideViewListener(new OnShowHideViewListener() {
//                    @Override
//                    public void onShowView(View view, int id) {
//                    }
//
//                    @Override
//                    public void onHideView(View view, int id) {
//                    }
//                }).onRetryListener(new OnRetryListener() {
//                    @Override
//                    public void onRetry() {
//                        loadData();//加载数据
//                    }
//                }).build();
//
//
//        //将多状态布局添加到内容布局中
//        View rootLayout = statusLayoutManager.getRootLayout();
//        if (llContent != null) {
//            llContent.addView(rootLayout);
//        }
//        statusLayoutManager.showContent();
//
//
//    }


    /**
     * 初始化WebView
     */
    private void initWebView() {
        webView.setWebViewClient(new WebViewClient() {

            /**
             *
             * @param view
             * @param url
             * @return true:表示当前url已经加载完成，即使url还会重定向都不会再进行加载 false 表示此url默认由系统处理，该重定向还是重定向，直到加载完成
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e(TAG, "shouldOverrideUrlLoading():url---" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.e(TAG, "onPageStarted():url---" + url);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtils.e(TAG, "onPageFinished():url---" + url);
                progressBar.setVisibility(View.GONE);
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtils.e(TAG, "onPageFinished():title---" + title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtils.e(TAG, "onProgressChanged():progress---" + newProgress);
                progressBar.setProgress(newProgress);

            }
        });


        webView.requestFocusFromTouch();
        //设置WebView属性，能够执行Javascript脚本
        WebSettings webSettings = webView.getSettings();

//        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
//        // 设置出现缩放工具
//        webSettings.setBuiltInZoomControls(true);
//        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
//        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        //设置webview的https与http内容混合属性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            webSettings.setJavaScriptEnabled(true);
//            webView.addJavascriptInterface(new JsObject(), "Android");
//        }

        //移除操作系统开放接口防止被恶意操作
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//API大于等于26
            //设置安全浏览模式
            webSettings.setSafeBrowsingEnabled(true);
        }

        //设置默认不保存密码
        webSettings.setSavePassword(false);

        //文件下载监听
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }


    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    protected void onWidgetClick(final View view) {
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
        super.onDestroy();

        if (unbinder != Unbinder.EMPTY) {
            unbinder.unbind();//解绑黄油刀
        }
    }
}
