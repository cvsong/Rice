package com.cvsong.study.rice.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cvsong.study.library.base.BaseActivity;
import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.library.wiget.statuslayout.OnRetryListener;
import com.cvsong.study.library.wiget.statuslayout.OnShowHideViewListener;
import com.cvsong.study.library.wiget.statuslayout.StatusLayoutManager;
import com.cvsong.study.library.wiget.titleview.CustomTitleView;
import com.cvsong.study.rice.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AppBaseActivity extends BaseActivity implements IBaseView {


    private long lastClick = 0;//上次点击时间
    protected StatusLayoutManager statusLayoutManager;
    private LinearLayout llContent;
    private Unbinder unbinder;
    protected CustomTitleView titleView;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemStyle();//设置主题样式
        setContentView(R.layout.activity_app_base);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏


        llContent = findViewById(R.id.ll_content);
        titleView = findViewById(R.id.title_view);

        initTitle();//初始化标题栏设置
        initStatusLayout();//初始化多状态布局
        unbinder = ButterKnife.bind(this);//绑定黄油刀
        activity=this;
        initView(savedInstanceState,llContent);//View初始化
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
        titleView.setLeftSubtitleText("返回");
        titleView.setTitleBackgroundColor(getResources().getColor(R.color.bg_blue_3fa9c4));
        titleView.setLeftSubTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//统一处理---->左边点击关闭当前页面
                Class<? extends AppBaseActivity> clazz = AppBaseActivity.this.getClass();
                ActivityUtils.finishActivity(clazz);
            }
        });
    }

    /**
     * 初始化多状态布局
     */
    private void initStatusLayout() {

        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(bindLayout())//绑定布局文件
                .emptyDataView(R.layout.layout_status_emptydata)
                .errorView(R.layout.layout_status_error)
                .loadingView(R.layout.layout_status_loading)
                .netWorkErrorView(R.layout.layout_status_networkerror)
                .retryViewId(R.id.button_retry)
                .onShowHideViewListener(new OnShowHideViewListener() {
                    @Override
                    public void onShowView(View view, int id) {
                    }

                    @Override
                    public void onHideView(View view, int id) {
                    }
                }).onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        loadData();//加载数据
                    }
                }).build();


        //将多状态布局添加到内容布局中
        View rootLayout = statusLayoutManager.getRootLayout();
        if (llContent != null) {
            llContent.addView(rootLayout);
        }
        statusLayoutManager.showContent();


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
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != Unbinder.EMPTY) {
            unbinder.unbind();//解绑黄油刀
        }
    }
}
