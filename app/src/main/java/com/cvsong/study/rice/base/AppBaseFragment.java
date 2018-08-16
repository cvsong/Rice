package com.cvsong.study.rice.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cvsong.study.library.base.BaseFragment;
import com.cvsong.study.library.wiget.statuslayout.OnRetryListener;
import com.cvsong.study.library.wiget.statuslayout.OnShowHideViewListener;
import com.cvsong.study.library.wiget.statuslayout.StatusLayoutManager;
import com.cvsong.study.rice.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 应用级BaseFragment
 */
public abstract class AppBaseFragment extends BaseFragment
        implements IBaseView {


    protected StatusLayoutManager statusLayoutManager;
    private Unbinder unbinder;
    private FrameLayout viewContent;
    private long lastClick = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_base, container, false);
        viewContent = (FrameLayout) view.findViewById(R.id.view_content);
        initStatusLayout();
        unbinder = ButterKnife.bind(this, view);//绑定黄油刀
        return view;
    }


    /**
     * 初始化多状态布局
     */
    private void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getActivity())
                .contentView(bindLayout())//绑定布局Id
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
                        loadData();
                    }
                }).build();

        //将多状态布局添加到内容布局中
        View rootLayout = statusLayoutManager.getRootLayout();
        viewContent.addView(rootLayout);
        statusLayoutManager.showContent();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState, view);//View初始化
        loadData();//数据加载
    }


    @Override
    public void onClick(View view) {
        if (!isFastClick()) {
            onWidgetClick(view);
        }
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
     * View点击
     *
     * @param view
     */
    protected void onWidgetClick(View view) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
    }


}
