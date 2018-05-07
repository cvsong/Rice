package com.cvsong.study.rice.base;

import android.os.Bundle;
import android.view.View;

import com.cvsong.study.common_library.base.BaseActivity;
import com.cvsong.study.rice.R;

public abstract class AppBaseActivity extends BaseActivity implements IBaseView {


    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_base);


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
}
