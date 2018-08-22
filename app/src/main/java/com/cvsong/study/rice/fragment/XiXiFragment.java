package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvsong.study.library.wiget.CircleImageView;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 嘻嘻Fragment
 * Created by chenweisong on 2018/05/25.
 */

public class XiXiFragment extends AppBaseFragment {

    @BindView(R.id.iv_head)
    CircleImageView ivHead;

    public static XiXiFragment newInstance() {
        XiXiFragment fragment = new XiXiFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_xixi;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        ivHead.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.iv_head:

                break;
        }
    }
}
