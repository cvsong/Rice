package com.cvsong.study.rice.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.cvsong.study.library.wiget.CustomBottomNavigationView;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.cvsong.study.rice.fragment.HaHaFragment;
import com.cvsong.study.rice.fragment.HeHeFragment;
import com.cvsong.study.rice.fragment.HiHiFragment;
import com.cvsong.study.rice.fragment.XiXiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页面
 * Created by chenweisong on 2018/5/24.
 */
public class HomeActivity extends AppBaseActivity {


    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_1)
    CustomBottomNavigationView bottom1;
    @BindView(R.id.bottom_2)
    CustomBottomNavigationView bottom2;
    @BindView(R.id.bottom_3)
    CustomBottomNavigationView bottom3;
    @BindView(R.id.bottom_4)
    CustomBottomNavigationView bottom4;
    private XiXiFragment xiXiFragment;
    private HaHaFragment haHaFragment;
    private HeHeFragment heHeFragment;
    private HiHiFragment hiHiFragment;
    private Fragment currentFragment;

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setLeftSubTitleVisibility(View.GONE);

        bottom1.setOnClickListener(this);
        bottom2.setOnClickListener(this);
        bottom3.setOnClickListener(this);
        bottom4.setOnClickListener(this);

        initFragment();//Fragment初始化

    }

    /**
     * Fragment初始化
     */
    private void initFragment() {
        haHaFragment = new HaHaFragment();
        heHeFragment = new HeHeFragment();
        hiHiFragment = new HiHiFragment();
        xiXiFragment = new XiXiFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, haHaFragment)
                .add(R.id.fl_content, heHeFragment)
                .add(R.id.fl_content, hiHiFragment)
                .add(R.id.fl_content, xiXiFragment)
                .hide(haHaFragment).hide(heHeFragment)
                .hide(hiHiFragment)
                .hide(xiXiFragment)
                .show(haHaFragment).commit();

        currentFragment = xiXiFragment;
        switchFragment(R.id.bottom_1);//切换到首页Fragement
    }

    @Override
    public void loadData() {


    }


    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);
        switchFragment(view.getId());
    }

    /**
     * Fragment切换
     *
     * @param childId
     */
    private void switchFragment(int childId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (childId) {
            case R.id.bottom_1:
                if (currentFragment instanceof HaHaFragment) {
                    return;
                }
                bottom1.setSelected(true);
                titleView.setTitleText("哈哈");
                transaction.hide(currentFragment).show(haHaFragment);
                currentFragment = haHaFragment;
                break;
            case R.id.bottom_2:
                if (currentFragment instanceof HeHeFragment) {
                    return;
                }
                titleView.setTitleText("呵呵");
                transaction.hide(currentFragment).show(heHeFragment);
                currentFragment = heHeFragment;
                break;

            case R.id.bottom_3:
                if (currentFragment instanceof HiHiFragment) {
                    return;
                }
                titleView.setTitleText("嘿嘿");
                transaction.hide(currentFragment).show(hiHiFragment);
                currentFragment = hiHiFragment;
                break;


            case R.id.bottom_4:
                if (currentFragment instanceof XiXiFragment) {
                    return;
                }
                titleView.setTitleText("嘻嘻");
                transaction.hide(currentFragment).show(xiXiFragment);
                currentFragment = xiXiFragment;
                break;

        }

        transaction.commit();

        bottom1.setSelected(currentFragment instanceof HaHaFragment ? true : false);
        bottom2.setSelected(currentFragment instanceof HeHeFragment ? true : false);
        bottom3.setSelected(currentFragment instanceof HiHiFragment ? true : false);
        bottom4.setSelected(currentFragment instanceof XiXiFragment ? true : false);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //防止应用崩溃时Fragment重影
//        super.onSaveInstanceState(outState, outPersistentState);
    }


}
