package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.activity.hehe.SilverInfoActivity;
import com.cvsong.study.rice.base.AppBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 呵呵Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HeHeFragment extends AppBaseFragment {

    @BindView(R.id.btn_silver_info)
    Button btnSilverInfo;

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
        btnSilverInfo.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);

        switch (view.getId()) {
            case R.id.btn_silver_info:
                ActivityUtils.startActivity(SilverInfoActivity.class);
                break;
        }
    }
}
