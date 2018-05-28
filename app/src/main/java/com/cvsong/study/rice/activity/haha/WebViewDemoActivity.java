package com.cvsong.study.rice.activity.haha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseWebViewActivity;

public class WebViewDemoActivity extends AppBaseWebViewActivity {


    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleText("百度搜索");
    }

    @Override
    public void loadData() {

        webView.loadUrl("http://www.baidu.com/");
    }
}
