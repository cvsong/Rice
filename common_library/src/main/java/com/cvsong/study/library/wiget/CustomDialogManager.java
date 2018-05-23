package com.cvsong.study.library.wiget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvsong.study.library.R;
import com.cvsong.study.library.util.CommonUtils;

/**
 * 自定义Dialog管理类
 * Created by chenweisong on 2018/5/22.
 */

public class CustomDialogManager {


    final Context context;
    final View body;
    final String titleText;
    final String contentText;
    final String positiveButtonText;
    final String negativeButtonText;
    final DialogInterface.OnClickListener positiveListener;
    final DialogInterface.OnClickListener negativeListener;
    final boolean touchOutside;
    final boolean cancelable;
    final boolean showCloseImg;
    final View.OnClickListener onCloseClickListener;

    private CustomDialogManager(Builder builder) {
        this.context = builder.context;
        this.body = builder.body;
        this.titleText = builder.titleText;
        this.contentText = builder.contentText;
        this.positiveButtonText = builder.positiveButtonText;
        this.negativeButtonText = builder.negativeButtonText;
        this.positiveListener = builder.positiveListener;
        this.negativeListener = builder.negativeListener;
        this.touchOutside = builder.touchOutside;
        this.cancelable = builder.cancelable;
        this.showCloseImg = builder.showCloseImg;
        this.onCloseClickListener = builder.onCloseClickListener;
    }


    /**
     * 显示Dialog
     */
    public AlertDialog show() {

        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.dialog_custom).create();
        alertDialog.show();

        Window window = alertDialog.getWindow();
        //解决AlertDialog无法弹出输入框问题
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        window.setContentView(R.layout.dialog_common_custom);
        TextView tvTitle = (TextView) window.findViewById(R.id.tv_title);
        FrameLayout flBody = (FrameLayout) window.findViewById(R.id.fl_body);
        LinearLayout llBottomBtns = (LinearLayout) window.findViewById(R.id.ll_bottom_btns);
        TextView tvTxt = (TextView) window.findViewById(R.id.tv_txt);
        Button btnPositive = (Button) window.findViewById(R.id.btn_custom_dialog_sure);
        Button btnNegative = (Button) window.findViewById(R.id.btn_custom_dialog_cancle);
        ImageView ivClose = (ImageView) window.findViewById(R.id.iv_cancle);

        //标题设置
        if (!TextUtils.isEmpty(titleText)) {// 包含标题
            tvTitle.setText(titleText);
        } else {// 不包含标题
            tvTitle.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) flBody.getLayoutParams();
            lp.topMargin = 0;
            flBody.setLayoutParams(lp);
        }


        //内容设置
        if (body != null) {// 包含内容View的时候，显示内容View
            tvTxt.setVisibility(View.GONE);
            flBody.addView(body);
        } else if (!TextUtils.isEmpty(contentText)) {// 包含内容文本的时候，显示内容文本
            tvTxt.setText(contentText);
        } else {
            tvTxt.setVisibility(View.GONE);
        }


        //取消按钮设置
        if (TextUtils.isEmpty(negativeButtonText)) {
            btnNegative.setVisibility(View.GONE);
        } else {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setText(negativeButtonText);
            //取消按钮点击事件监听
            btnNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (negativeListener == null) {
                        alertDialog.cancel();
                    } else {
                        negativeListener.onClick(alertDialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                }
            });
        }


        //确认按钮设置
        if (TextUtils.isEmpty(positiveButtonText)) {
            btnPositive.setVisibility(View.GONE);
        } else {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setText(positiveButtonText);
            //确认按钮点击事件监听
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (positiveListener == null) {
                        alertDialog.dismiss();
                    } else {
                        positiveListener.onClick(alertDialog, DialogInterface.BUTTON_POSITIVE);
                    }
                }
            });
        }

        //底部按钮父控件设置
        llBottomBtns.setVisibility((TextUtils.isEmpty(positiveButtonText) && TextUtils.isEmpty(negativeButtonText)) ? View.GONE : View.VISIBLE);


        //关闭图标设置
        ivClose.setVisibility(showCloseImg ? View.VISIBLE : View.GONE);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCloseClickListener != null) {
                    onCloseClickListener.onClick(v);
                } else {
                    alertDialog.cancel();
                }
            }
        });


        WindowManager.LayoutParams params = window.getAttributes();
        params.width = context.getResources().getDisplayMetrics().widthPixels
                - CommonUtils.Dp2Px(context, 30 * 2);
        window.setAttributes(params);
        //设置能否点击外部区域取消Dialog
        alertDialog.setCanceledOnTouchOutside(touchOutside);
        //设置能否点Back键取消Dialog
        alertDialog.setCancelable(cancelable);

        return alertDialog;
    }


    public static final class Builder {
        private Context context;
        private View body;
        private String titleText;
        private String contentText;
        private String positiveButtonText;
        private String negativeButtonText;
        private DialogInterface.OnClickListener positiveListener;
        private DialogInterface.OnClickListener negativeListener;
        private boolean touchOutside;
        private boolean cancelable;
        private boolean showCloseImg;
        private View.OnClickListener onCloseClickListener;


        public Builder(Context context) {
            this.context = context;
        }


        public Builder body(View body) {
            this.body = body;
            return this;
        }

        public Builder titleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public Builder contentText(String contentText) {
            this.contentText = contentText;
            return this;
        }

        public Builder positiveButtonText(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }

        public Builder negativeButtonText(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public Builder positiveListener(DialogInterface.OnClickListener positiveListener) {
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder negativeListener(DialogInterface.OnClickListener negativeListener) {
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder touchOutside(boolean touchOutside) {
            this.touchOutside = touchOutside;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder showCloseImg(boolean showCloseImg) {
            this.showCloseImg = showCloseImg;
            return this;
        }

        public Builder onCloseClickListener(View.OnClickListener onCloseClickListener) {
            this.onCloseClickListener = onCloseClickListener;
            return this;
        }

        public CustomDialogManager build() {
            return new CustomDialogManager(this);
        }
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }
}
