package com.cvsong.study.rice.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cvsong.study.library.ndk.JniUtil;
import com.cvsong.study.library.util.CommonUtils;
import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseActivity;

import butterknife.BindView;

public class MainActivity extends AppBaseActivity {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button button;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleText("主页面");
        titleView.setLeftSubTitleVisibility(View.GONE);
        button.setOnClickListener(this);

    }


    @Override
    public void loadData() {

    }


    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);
        switch (view.getId()) {

            case R.id.button:

                String rsaKey = new JniUtil().getRsaKey(this);
                LogUtils.e(rsaKey);

                break;
        }
    }
}
