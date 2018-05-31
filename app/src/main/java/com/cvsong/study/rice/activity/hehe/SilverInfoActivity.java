package com.cvsong.study.rice.activity.hehe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.cvsong.study.library.net.entity.HttpCallBack;
import com.cvsong.study.library.net.entity.Result;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.cvsong.study.rice.entity.SilverInfoEntity;
import com.cvsong.study.rice.manager.http.AppHttpManage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 白银数据展示页面
 * Created by chenweisong on 2018/5/31.
 */
public class SilverInfoActivity extends AppBaseActivity {


    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvClosePri)
    TextView tvClosePri;
    @BindView(R.id.tvHighPic)
    TextView tvHighPic;
    @BindView(R.id.tvLimit)
    TextView tvLimit;
    @BindView(R.id.tvLowPic)
    TextView tvLowPic;
    @BindView(R.id.tvOpenPri)
    TextView tvOpenPri;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvTotalTurnover)
    TextView tvTotalTurnover;
    @BindView(R.id.tvTotalVol)
    TextView tvTotalVol;
    @BindView(R.id.tvVariety)
    TextView tvVariety;
    @BindView(R.id.tvYesterdayPic)
    TextView tvYesterdayPic;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    public int bindLayout() {
        return R.layout.activity_silver_info;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleText("白银现货");
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void loadData() {
        AppHttpManage.getSilverInfo(activity, new HttpCallBack<List<SilverInfoEntity.ResultEntity>>() {
            @Override
            public void onSuccess(Result result, List<SilverInfoEntity.ResultEntity> entity) {
                super.onSuccess(result, entity);
                refreshLayout.finishRefresh();
                bindData(entity);
            }
        });

    }

    //绑定数据
    private void bindData(List<SilverInfoEntity.ResultEntity> entity) {
        if (entity == null || entity.size() == 0) {
            return;
        }

        SilverInfoEntity.ResultEntity resultEntity = entity.get(0);
        tvName.setText(resultEntity.getName());
        tvClosePri.setText(resultEntity.getClosePri());
        tvHighPic.setText(resultEntity.getHighPic());
        tvLimit.setText(resultEntity.getLimit());
        tvLowPic.setText(resultEntity.getLowPic());
        tvOpenPri.setText(resultEntity.getOpenPri());
        tvTime.setText(resultEntity.getTime());
        tvTotalTurnover.setText(resultEntity.getTotalTurnover());
        tvTotalVol.setText(resultEntity.getTotalVol());
        tvVariety.setText(resultEntity.getVariety());
        tvYesterdayPic.setText(resultEntity.getYesDayPic());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
