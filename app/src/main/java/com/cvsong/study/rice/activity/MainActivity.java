package com.cvsong.study.rice.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.cvsong.study.library.util.app_tools.AppSpUtils;
import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.activity.start.StartGuideActivity;
import com.cvsong.study.rice.base.AppBaseActivity;

public class MainActivity extends AppBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleVisibility(View.GONE);

    }


    @Override
    public void loadData() {

        //TODO 6.0权限申请

        //TODO 更新判断

        //判断是否跳转引导页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                judgeIsSkipGuidePage();
            }
        }, 5000);



    }

    /**
     * 判断是否跳转引导页
     */
    private void judgeIsSkipGuidePage() {
        //是否再次打开
        boolean isOpenAgain = AppSpUtils.getInstance().getBoolean(AppSpUtils.IS_OPEN_AGAIN);
        if (!isOpenAgain) {//第一次启动应用--->启动引导页面
            ActivityUtils.finishActivity(MainActivity.class);
            ActivityUtils.startActivity(StartGuideActivity.class);
        } else {//再次启动-->主页面
            ActivityUtils.finishActivity(MainActivity.class);
            ActivityUtils.startActivity(HomeActivity.class);
        }
    }


}
