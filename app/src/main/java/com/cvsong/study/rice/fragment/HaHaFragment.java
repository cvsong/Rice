package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.view.View;

import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;


/**
 * 哈哈Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HaHaFragment extends AppBaseFragment {

    public static HaHaFragment newInstance() {
        HaHaFragment fragment = new HaHaFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_haha;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void loadData() {

    }
}
