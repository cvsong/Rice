package com.cvsong.study.rice.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.entity.SilverInfoEntity;

import java.util.List;

/**
 * 贵金属白银数据展示Adapter
 * Created by chenweisong on 2018/6/1.
 */

public class RvSilverInfoAdapter extends BaseQuickAdapter<SilverInfoEntity.ResultEntity, BaseViewHolder> {

    public RvSilverInfoAdapter(@Nullable List<SilverInfoEntity.ResultEntity> data) {

        super(R.layout.item_silver_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SilverInfoEntity.ResultEntity item) {

        helper.setText(R.id.tvName, item.getName());
        helper.setText(R.id.tvClosePri, item.getClosePri());
        helper.setText(R.id.tvHighPic, item.getHighPic());
        helper.setText(R.id.tvLimit, item.getLimit());
        helper.setText(R.id.tvLowPic, item.getLowPic());
        helper.setText(R.id.tvOpenPri, item.getOpenPri());
        helper.setText(R.id.tvTime, item.getTime());
        helper.setText(R.id.tvTotalTurnover, item.getTotalTurnover());
        helper.setText(R.id.tvTotalVol, item.getTotalVol());
        helper.setText(R.id.tvVariety, item.getVariety());
        helper.setText(R.id.tvYesterdayPic, item.getYesDayPic());
    }
}
