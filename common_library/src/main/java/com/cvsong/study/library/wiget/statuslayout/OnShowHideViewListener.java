package com.cvsong.study.library.wiget.statuslayout;

import android.view.View;

/**
 * 显示隐藏监听回调
 */
public interface OnShowHideViewListener {

    void onShowView(View view, int id);

    void onHideView(View view, int id);
}
