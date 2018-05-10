package com.cvsong.study.rice.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 应用级BaseActivity接口
 */
interface IBaseView extends View.OnClickListener {


    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    int bindLayout();


    /**
     * 初始化 view
     */
    void initView(final Bundle savedInstanceState,View view);



    /**
     * 加载数据
     */
    void loadData();



}
