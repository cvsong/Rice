package com.cvsong.study.rice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cvsong.study.common_library.util.CommonUtils;
import com.cvsong.study.common_library.util.utilcode.util.LogUtils;
import com.cvsong.study.common_library.wiget.titleview.StatusBarUtil;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.cvsong.study.rice.ndk.JniUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

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

                String appVersionName = CommonUtils.getAppVersionName(this);
                LogUtils.e(appVersionName);

                break;
        }
    }
}
