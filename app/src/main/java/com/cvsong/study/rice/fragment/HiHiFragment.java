package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cvsong.study.library.andfix.AndFixUtils;
import com.cvsong.study.library.andfix.PatchFilePathManager;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 嘿嘿Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HiHiFragment extends AppBaseFragment {


    private static final String TAG = HiHiFragment.class.getSimpleName();
    @BindView(R.id.btn_make_bug1)
    Button btnMakeBug1;
    @BindView(R.id.btn_fix_bug1)
    Button btnFixBug1;
    @BindView(R.id.btn_make_bug2)
    Button btnMakeBug2;
    @BindView(R.id.btn_fix_bug2)
    Button btnFixBug2;
    Unbinder unbinder;


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

        try {
            File file = new File(PatchFilePathManager.PATCH_DIR);
            if (file == null || !file.exists()) {
                file.mkdir();
                Log.e(TAG, "文件夹新建成功");
            } else {
                Log.e(TAG, "文件夹存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        Log.e(TAG, PatchFilePathManager.PATCH_FILE_NAME1);
        //  /storage/emulated/0/apatch/study1.apatch
        AndFixUtils.getInstance().addPatch(PatchFilePathManager.PATCH_FILE_NAME1);

    }


     /**
     * 产生Bug
     */
    private void makeBug2() {
//
//        int x = 5/2;
//
//        Log.e(TAG, String.valueOf(x));

    }


    /**
     * 修复Bug
     */
    private void fixBug2() {

        Log.e(TAG, PatchFilePathManager.PATCH_FILE_NAME2);
        //  /storage/emulated/0/apatch/study2.apatch
        AndFixUtils.getInstance().addPatch(PatchFilePathManager.PATCH_FILE_NAME2);

    }




}
