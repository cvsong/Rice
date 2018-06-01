package com.cvsong.study.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**顶级BaseFragment
 * Created by chenweisong on 2018/6/1.
 */

public class BaseFragment extends Fragment {

    protected FragmentActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

}
