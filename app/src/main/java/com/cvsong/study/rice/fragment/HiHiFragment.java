package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.view.View;

import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;


/**
 * 嘿嘿Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HiHiFragment extends AppBaseFragment {

    public static HiHiFragment newInstance() {
        HiHiFragment fragment = new HiHiFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_hihi;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void loadData() {

    }
}
