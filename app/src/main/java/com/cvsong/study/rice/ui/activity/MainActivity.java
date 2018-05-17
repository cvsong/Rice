package com.cvsong.study.rice.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cvsong.study.library.ndk.JniUtil;
import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppBaseActivity {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleText("主页面");
        titleView.setLeftSubTitleVisibility(View.GONE);
        button.setOnClickListener(this);


        initRefreshLayout();

    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

//        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
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
