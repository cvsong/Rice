package com.cvsong.study.rice.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.cvsong.study.library.wiget.bottomview.BottomBar;
import com.cvsong.study.library.wiget.bottomview.BottomBarTab;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.cvsong.study.rice.fragment.HaHaFragment;
import com.cvsong.study.rice.fragment.HeHeFragment;
import com.cvsong.study.rice.fragment.HiHiFragment;
import com.cvsong.study.rice.fragment.XiXiFragment;

import butterknife.BindView;

/**
 * 主页面
 * Created by chenweisong on 2018/5/24.
 */
public class HomeActivity extends AppBaseActivity {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    private Fragment currentFragment;

    private Fragment[] fragments = new Fragment[4];
    private String[] titles = new String[]{"哈哈", "呵呵", "嘿嘿", "嘻嘻"};
    private int[] unselectIcons = new int[]{R.drawable.icon_tab_wd_d, R.drawable.icon_tab_wd_d, R.drawable.icon_tab_wd_d, R.drawable.icon_tab_wd_d};
    private int[] selectedIcons = new int[]{R.drawable.icon_tab_wd_l, R.drawable.icon_tab_wd_l, R.drawable.icon_tab_wd_l, R.drawable.icon_tab_wd_l};


    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setLeftSubTitleVisibility(View.GONE);

        initFragment();//Fragment初始化
        initBottomView();//初始化底部导航栏

    }

    /**
     * Fragment初始化
     */
    private void initFragment() {
        fragments[FIRST] = HaHaFragment.newInstance();
        fragments[SECOND] = HeHeFragment.newInstance();
        fragments[THIRD] = HiHiFragment.newInstance();
        fragments[FOURTH] = XiXiFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            transaction.add(R.id.fl_content, fragment);
            transaction.hide(fragment);
        }
        transaction.show(fragments[FIRST]);
        transaction.commitAllowingStateLoss();
        currentFragment = fragments[FIRST];
        titleView.setTitleText(titles[FIRST]);

    }


    /**
     * 初始化底部导航栏
     */
    private void initBottomView() {
        BottomBarTab firstTab = new BottomBarTab(this, unselectIcons[FIRST], selectedIcons[FIRST], titles[FIRST]);
        BottomBarTab secondTab = new BottomBarTab(this, unselectIcons[SECOND], selectedIcons[SECOND], titles[SECOND]);
        BottomBarTab thirdTab = new BottomBarTab(this, unselectIcons[THIRD], selectedIcons[THIRD], titles[THIRD]);
        BottomBarTab fourthTab = new BottomBarTab(this, unselectIcons[FOURTH], selectedIcons[FOURTH], titles[FOURTH]);
        bottomBar.addItem(firstTab)
                .addItem(secondTab)
                .addItem(thirdTab)
                .addItem(fourthTab);

        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                if (position == prePosition) {
                    return;
                }
                switchFragment(position);//切换Fragment
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }


    /**
     * Fragment切换
     */
    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragments[position];
        transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        titleView.setTitleText(titles[position]);
        currentFragment = fragment;
    }

    @Override
    public void loadData() {


    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //防止应用崩溃时Fragment重影
//        super.onSaveInstanceState(outState, outPersistentState);
    }


}
