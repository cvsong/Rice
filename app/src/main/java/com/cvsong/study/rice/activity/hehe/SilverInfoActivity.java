package com.cvsong.study.rice.activity.hehe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cvsong.study.library.net.entity.HttpCallBack;
import com.cvsong.study.library.net.entity.Result;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.adapter.RvSilverInfoAdapter;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.cvsong.study.rice.entity.SilverInfoEntity;
import com.cvsong.study.rice.manager.http.AppHttpManage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 白银数据展示页面
 * Created by chenweisong on 2018/5/31.
 */
public class SilverInfoActivity extends AppBaseActivity {


    @BindView(R.id.rv_silver)
    RecyclerView rvSilver;
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

            @Override
            public void onFailure(Request request, Exception exception) {
                super.onFailure(request, exception);
                refreshLayout.finishRefresh();
            }
        });

    }

    //绑定数据
    private void bindData(List<SilverInfoEntity.ResultEntity> entity) {
        rvSilver.setLayoutManager(new LinearLayoutManager(this));
        rvSilver.setAdapter(new RvSilverInfoAdapter(entity));

    }


}
