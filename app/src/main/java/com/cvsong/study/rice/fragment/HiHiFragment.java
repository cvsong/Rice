package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;

import butterknife.BindView;


/**
 * 嘿嘿Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HiHiFragment extends AppBaseFragment {


    @BindView(R.id.btn_make_bug1)
    Button btnMakeBug1;
    @BindView(R.id.btn_fix_bug1)
    Button btnFixBug1;
    @BindView(R.id.btn_make_bug2)
    Button btnMakeBug2;
    @BindView(R.id.btn_fix_bug2)
    Button btnFixBug2;


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
        btnMakeBug1.setOnClickListener(this);
        btnMakeBug2.setOnClickListener(this);
        btnFixBug1.setOnClickListener(this);
        btnFixBug2.setOnClickListener(this);

    }

    @Override
    public void loadData() {


    }


    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.btn_make_bug1:
                makeBug1();//产生Bug
                break;
            case R.id.btn_fix_bug1:
                fixBug1();//修复Bug
                break;

             case R.id.btn_make_bug2:
                makeBug2();//产生Bug
                break;
            case R.id.btn_fix_bug2:
                fixBug2();//修复Bug
                break;



        }

    }

    /**
     * 产生Bug
     */
    private void makeBug1() {

        String error = "Hello AndFix!!!!!!!!!!";
//        String error = null;

        Log.e(TAG, error);

    }


    /**
     * 修复Bug
     */
    private void fixBug1() {

        //  /storage/emulated/0/apatch/study1.apatch

    }


     /**
     * 产生Bug
     */
    private void makeBug2() {

    }


    /**
     * 修复Bug
     */
    private void fixBug2() {


    }




}
