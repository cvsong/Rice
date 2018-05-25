package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.view.View;

import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;


/**
 * 呵呵Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HeHeFragment extends AppBaseFragment {

    public static HeHeFragment newInstance() {
        HeHeFragment fragment = new HeHeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_hehe;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void loadData() {

    }
}
