package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cvsong.study.library.andfix.AndFixManager;
import com.cvsong.study.library.andfix.PatchFilePathManager;
import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 嘿嘿Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class HiHiFragment extends AppBaseFragment {

    @BindView(R.id.btn_make_bug)
    Button btnMakeBug;
    @BindView(R.id.btn_fix_bug)
    Button btnFixBug;

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
        btnMakeBug.setOnClickListener(this);
        btnFixBug.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }


    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.btn_make_bug:
                makeBug();//产生Bug
                break;
            case R.id.btn_fix_bug:
                fixBug();//修复Bug
                break;
        }

    }

    /**
     * 产生Bug
     */
    private void makeBug() {

        String error = null;

        Log.e(TAG, error);

    }


    /**
     * 修复Bug
     */
    private void fixBug() {

        LogUtils.e(TAG,PatchFilePathManager.PATCH_FILE_NAME);
        AndFixManager.getInstance().addPatch(PatchFilePathManager.PATCH_FILE_NAME);

    }
}
