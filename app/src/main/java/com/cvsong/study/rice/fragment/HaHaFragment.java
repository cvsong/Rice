package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.activity.haha.CustomScanActivity;
import com.cvsong.study.rice.activity.haha.WebViewDemoActivity;
import com.cvsong.study.rice.base.AppBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 哈哈Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HaHaFragment extends AppBaseFragment {

    @BindView(R.id.btn_open_baidu)
    Button btnOpenBaidu;
    @BindView(R.id.btn_open_camera)
    Button btnOpenCamera;
    @BindView(R.id.btn_qr_scan)
    Button btnQrScan;


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
        btnOpenBaidu.setOnClickListener(this);
        btnOpenCamera.setOnClickListener(this);
        btnQrScan.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);
        switch (view.getId()) {
            case R.id.btn_open_baidu://打开百度
                ActivityUtils.startActivity(WebViewDemoActivity.class);

                break;
            case R.id.btn_open_camera://打开图片选择
                ActivityUtils.startActivity(CustomScanActivity.class);

                break;

            case R.id.btn_qr_scan://二维码扫描
                ActivityUtils.startActivity(CustomScanActivity.class);

                break;


        }
    }


}
