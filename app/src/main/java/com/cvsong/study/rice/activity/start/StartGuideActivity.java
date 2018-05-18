package com.cvsong.study.rice.activity.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseActivity;

public class StartGuideActivity extends AppBaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_start_guide;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleText("引导页");
    }

    @Override
    public void loadData() {

    }
}
