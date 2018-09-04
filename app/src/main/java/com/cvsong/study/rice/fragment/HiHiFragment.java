package com.cvsong.study.rice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.library.webservice.SoapManager;
import com.cvsong.study.library.webservice.WebServiceUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseFragment;
import com.cvsong.study.rice.entity.SilverInfoEntity;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        //添加参数
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("byProvinceName", "");
        //通过工具类调用WebService接口

        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "getSupportProvince", null, new WebServiceUtils.WebServiceCallBack() {

            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject result) {
                if(result != null){
                    String json = SoapManager.getInstance().soapToJson(result, SilverInfoEntity.ResultEntity.class);
                    LogUtils.e(TAG,json);
                    List<String> provinceList = parseSoapObject(result);
                    LogUtils.i(TAG, provinceList);

                }else{
                    Toast.makeText(activity, "获取WebService数据错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    /**
     * 解析SoapObject对象
     * @param result
     * @return
     */
    private List<String> parseSoapObject(SoapObject result){
        List<String> list = new ArrayList<String>();
        SoapObject provinceSoapObject = (SoapObject) result.getProperty("getSupportProvinceResult");
        if(provinceSoapObject == null) {
            return null;
        }
        for(int i=0; i<provinceSoapObject.getPropertyCount(); i++){
            list.add(provinceSoapObject.getProperty(i).toString());
        }

        return list;
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
