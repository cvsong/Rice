package com.cvsong.study.rice.activity.haha;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cvsong.study.library.util.LightSensorUtil;
import com.cvsong.study.library.util.permission.AppPermissionEntity;
import com.cvsong.study.library.util.permission.AppPermissionsManager;
import com.cvsong.study.library.util.permission.AppSettingsHolderActivity;
import com.cvsong.study.library.util.permission.IPermissionCallbacks;
import com.cvsong.study.library.util.permission.PermissionRequestCallback;
import com.cvsong.study.library.util.utilcode.util.ToastUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomScanActivity extends AppBaseActivity {


    @BindView(R.id.barcode_scanner)
    DecoratedBarcodeView barcodeScanner;
    @BindView(R.id.cb_flashlight)
    CheckBox cbFlashlight;
    @BindView(R.id.tv_result)
    TextView tvResult;
    private IPermissionCallbacks permissionCallbacks = new PermissionRequestCallback() {
        @Override
        public void onAllPermissionsGranted(int requestCode, @NonNull List<AppPermissionEntity> appPermissions) {
            super.onAllPermissionsGranted(requestCode, appPermissions);
            initCamera();//初始化相机设置
        }
    };

    private AppPermissionEntity[] perms;
    private CaptureManager capture;
    private BeepManager beepManager;
    private String lastText;
    private LightSensorUtil instance;

    @Override
    public int bindLayout() {
        return R.layout.activity_custom_scan;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleText("二维码扫描");
//        titleView.setTitleBackgroundColor(getResources().getColor(R.color.transparent));

        requestCameraPermission();//请求相机权限
    }

    /**
     * 请求相机权限
     */
    private void requestCameraPermission() {
        perms = new AppPermissionEntity[]{new AppPermissionEntity(Manifest.permission.CAMERA, "相机")};
        AppPermissionsManager.requestPermissions(this, permissionCallbacks, perms);
    }

    @Override
    public void loadData() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AppPermissionsManager.onRequestPermissionsResult(this, requestCode, perms, grantResults, permissionCallbacks);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsHolderActivity.RC_APP_SETTING) {//跳转设置页面
            requestCameraPermission();//请求相机权限
        }
    }


    /**
     * 初始化相机设置
     */
    private void initCamera() {
        beepManager = new BeepManager(this);//声音震动管理器
        barcodeScanner.setTorchListener(new DecoratedBarcodeView.TorchListener() {
            @Override
            public void onTorchOn() {
                //闪光灯打开
            }

            @Override
            public void onTorchOff() {
                //闪光灯关闭
            }
        });
        capture = new CaptureManager(this, barcodeScanner);
        barcodeScanner.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                final String resultText = result.getText();
                if (resultText == null || !TextUtils.isEmpty(lastText)) {
                    return;
                }
                lastText = resultText;
                //扫码结果处理
                beepManager.playBeepSoundAndVibrate();//播放声音
                tvResult.setText(String.format("扫描结果:%s",resultText));
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });


        cbFlashlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    barcodeScanner.setTorchOn();
                } else {
                    barcodeScanner.setTorchOff();
                }
            }
        });


        //判断是否有闪光灯
        if (!hasFlash()) {
            cbFlashlight.setVisibility(View.GONE);
        } else {//根据光感设置闪关灯的默认开关状态
//            instance = LightSensorUtil.getInstance();//光感管理工具
//            instance.start(this);
//            float lux = instance.getLux();
        }

        tvResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(lastText);
                ToastUtils.showShort("复制成功");
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (capture != null) {
            capture.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (capture != null) {
            capture.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (capture != null) {
            capture.onDestroy();
        }
        if (instance != null) {
            instance.stop();
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (capture != null) {
            capture.onSaveInstanceState(outState);
        }
    }


    /**
     * 判断是否有闪光灯
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
