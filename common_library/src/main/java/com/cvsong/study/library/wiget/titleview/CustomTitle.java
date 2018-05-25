package com.cvsong.study.library.wiget.titleview;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvsong.study.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * 自定义标题栏控件
 * Created by chenweisong on 2018/5/17.
 */

public class CustomTitle {


    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    /*颜色设置*/
    private final @ColorInt
    int titleBackgroundColor;//标题栏背景颜色(包括标题内容和状态栏)
    private final @ColorInt
    int titleContentBackgroundColor;//标题内容背景颜色
    private final @ColorInt
    int statusBarColor;//状态栏颜色
    private final @ColorInt
    int titleTextColor;//主标题字体颜色
    private final @ColorInt
    int leftSubtitleTextColor;//左边副标题字体颜色
    private final @ColorInt
    int rightSubtitleTextColor;//右边副标题字体颜色


    /*显示设置*/
    private final @CustomTitleView.Visibility
    int titleViewVisibility;//标题栏显隐设置
    private final @CustomTitleView.Visibility
    int leftSubtitleVisibility;//左边副标题显隐设置
    private final @CustomTitleView.Visibility
    int rightSubtitleVisibility;//右边副标题显隐设置
    private final @CustomTitleView.Visibility
    int leftIconVisibility;//左边副标题图标显隐设置
    private final @CustomTitleView.Visibility
    int rightIconVisibility;//右边副标题图标显隐设置
    private final @CustomTitleView.Visibility
    int titleTextVisibility;//主标题文本显隐设置
    private final @CustomTitleView.Visibility
    int rightTextVisibility;//右边副标题文本显隐设置

    private final @CustomTitleView.Visibility
    int leftTextVisibility;//左边副标题文本标显隐设置


    /*标题文本设置*/
    private final CharSequence titleText;//标题文本
    private final CharSequence leftSubtitleText;//左副标题文本
    private final CharSequence rightSubtitleText;//右标题文本


    /*文字大小设置*/
    private final float titleSize;//标题文字大小
    private final float leftSubtitleSize;//左标题文字大小
    private final float rightSubtitleSize;//右标题文字大小


    /*图标设置*/
    private final @DrawableRes
    int leftIconRes;//左边图标
    private final @DrawableRes
    int rightIconRes;//右边图标


    /*监听设置*/
    private final View.OnClickListener leftSubtitleClickListener;//左标题点击监听
    private final View.OnClickListener rightSubtitleClickListener;//右标题点击监听


    private RelativeLayout viewTitle;
    private RelativeLayout subtitleLeft;
    private ImageView ivSubtitleLeft;
    private TextView tvSubtitleLeft;
    private RelativeLayout subtitleRight;
    private ImageView ivSubtitleRight;
    private TextView tvSubtitleRight;
    private TextView tvTitle;


    private CustomTitle(Builder builder, Activity context) {
        titleBackgroundColor = builder.titleBackgroundColor;
        titleContentBackgroundColor = builder.titleContentBackgroundColor;
        statusBarColor = builder.statusBarColor;
        titleTextColor = builder.titleTextColor;
        leftSubtitleTextColor = builder.leftSubtitleTextColor;
        rightSubtitleTextColor = builder.rightSubtitleTextColor;
        titleViewVisibility = builder.titleViewVisibility;
        leftSubtitleVisibility = builder.leftSubtitleVisibility;
        rightSubtitleVisibility = builder.rightSubtitleVisibility;
        leftIconVisibility = builder.leftIconVisibility;
        rightIconVisibility = builder.rightIconVisibility;
        titleTextVisibility=builder.titleTextVisibility;
        rightTextVisibility = builder.rightTextVisibility;
        leftTextVisibility = builder.leftTextVisibility;
        titleText = builder.titleText;
        leftSubtitleText = builder.leftSubtitleText;
        rightSubtitleText = builder.rightSubtitleText;
        titleSize = builder.titleSize;
        leftSubtitleSize = builder.leftSubtitleSize;
        rightSubtitleSize = builder.rightSubtitleSize;
        leftIconRes = builder.leftIconRes;
        rightIconRes = builder.rightIconRes;
        leftSubtitleClickListener = builder.leftSubtitleClickListener;
        rightSubtitleClickListener = builder.rightSubtitleClickListener;

        initView(context);//View初始化
    }

    public static Builder newBuilder(Activity activity) {
        return new Builder(activity);
    }


    /**
     * View初始化
     *
     * @param context
     */
    private void initView(Activity context) {

        /*整个标题栏*/
        viewTitle = context.findViewById(R.id.view_title);

        /*主标题*/
        tvTitle = viewTitle.findViewById(R.id.tv_title_name);

        /*左边副标题*/
        subtitleLeft = viewTitle.findViewById(R.id.rl_subtitle_left);
        ivSubtitleLeft = viewTitle.findViewById(R.id.iv_subtitle_left);
        tvSubtitleLeft = viewTitle.findViewById(R.id.tv_subtitle_left);


        /*右边副标题*/
        subtitleRight = viewTitle.findViewById(R.id.rl_subtitle_right);
        ivSubtitleRight = viewTitle.findViewById(R.id.iv_subtitle_right);
        tvSubtitleRight = viewTitle.findViewById(R.id.tv_subtitle_right);

        setTitle(context);//设置Title

    }

    /**
     * 设置标题
     * @param context
     */
    private void setTitle(Activity context) {

        //颜色设置
        viewTitle.setBackgroundColor(titleBackgroundColor);//标题背景颜色
        StatusBarUtil.setColor(context, statusBarColor);//状态栏颜色
        tvTitle.setTextColor(titleTextColor);//主标题字体颜色
        tvSubtitleLeft.setTextColor(leftSubtitleTextColor);//左副标题字体颜色
        tvSubtitleRight.setTextColor(rightSubtitleTextColor);//右副标题字体颜色


        //显隐设置
        viewTitle.setVisibility(titleViewVisibility);//主标题显隐
        subtitleLeft.setVisibility(leftSubtitleVisibility);//左标题显隐
        subtitleRight.setVisibility(rightSubtitleVisibility);//右标题显隐
        ivSubtitleLeft.setVisibility(leftIconVisibility);//左边图标显隐
        ivSubtitleRight.setVisibility(rightIconVisibility);//右边图标显隐
        tvSubtitleLeft.setVisibility(leftTextVisibility);//左文本显隐
        tvSubtitleRight.setVisibility(rightTextVisibility);//右文本显隐


        //图标设置
        ivSubtitleLeft.setImageResource(leftIconRes);//左边图标
        ivSubtitleRight.setImageResource(rightIconRes);//右边图标

        //文本内容设置
        tvTitle.setText(titleText);//标题名称
        tvSubtitleLeft.setText(leftSubtitleText);//左边文本
        tvSubtitleRight.setText(rightSubtitleText);//右边文本

        //文本字体大小设置
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,titleSize);//主标题文字大小
        tvSubtitleLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP,leftSubtitleSize);//左标题文字大小
        tvSubtitleRight.setTextSize(TypedValue.COMPLEX_UNIT_SP,rightSubtitleSize);//右标题文字大小

        //监听设置
        subtitleLeft.setOnClickListener(leftSubtitleClickListener);//左边点击监听
        subtitleRight.setOnClickListener(rightSubtitleClickListener);//右边点击监听









    }


    public static final class Builder {
        private final Activity activity;
        private int titleBackgroundColor;
        private int titleContentBackgroundColor;
        private int statusBarColor;
        private int titleTextColor;
        private int leftSubtitleTextColor;
        private int rightSubtitleTextColor;
        private int titleViewVisibility;
        private int leftSubtitleVisibility;
        private int rightSubtitleVisibility;
        private int leftIconVisibility;
        private int rightIconVisibility;
        private int titleTextVisibility;
        private int rightTextVisibility;
        private int leftTextVisibility;
        private CharSequence titleText;
        private CharSequence leftSubtitleText;
        private CharSequence rightSubtitleText;
        private float titleSize;
        private float leftSubtitleSize;
        private float rightSubtitleSize;
        private int leftIconRes;
        private int rightIconRes;
        private View.OnClickListener leftSubtitleClickListener;
        private View.OnClickListener rightSubtitleClickListener;

        private Builder(Activity activity) {
            this.activity=activity;
        }

        public Builder setTitleBackgroundColor(int val) {
            titleBackgroundColor = val;
            return this;
        }

        public Builder setTitleContentBackgroundColor(int val) {
            titleContentBackgroundColor = val;
            return this;
        }

        public Builder setStatusBarColor(int val) {
            statusBarColor = val;
            return this;
        }

        public Builder setTitleTextColor(int val) {
            titleTextColor = val;
            return this;
        }

        public Builder setLeftSubtitleTextColor(int val) {
            leftSubtitleTextColor = val;
            return this;
        }

        public Builder setRightSubtitleTextColor(int val) {
            rightSubtitleTextColor = val;
            return this;
        }

        public Builder setTitleViewVisibility(int val) {
            titleViewVisibility = val;
            return this;
        }

        public Builder setLeftSubtitleVisibility(int val) {
            leftSubtitleVisibility = val;
            return this;
        }

        public Builder setRightSubtitleVisibility(int val) {
            rightSubtitleVisibility = val;
            return this;
        }

        public Builder setLeftIconVisibility(int val) {
            leftIconVisibility = val;
            return this;
        }

        public Builder setRightIconVisibility(int val) {
            rightIconVisibility = val;
            return this;
        }

        public Builder setTitleTextVisibility(int val) {
            titleTextVisibility = val;
            return this;
        }

        public Builder setRightTextVisibility(int val) {
            rightTextVisibility = val;
            return this;
        }

        public Builder setLeftTextVisibility(int val) {
            leftTextVisibility = val;
            return this;
        }

        public Builder setTitleText(CharSequence val) {
            titleText = val;
            return this;
        }

        public Builder setLeftSubtitleText(CharSequence val) {
            leftSubtitleText = val;
            return this;
        }

        public Builder setRightSubtitleText(CharSequence val) {
            rightSubtitleText = val;
            return this;
        }

        public Builder setTitleSize(float val) {
            titleSize = val;
            return this;
        }

        public Builder setLeftSubtitleSize(float val) {
            leftSubtitleSize = val;
            return this;
        }

        public Builder setRightSubtitleSize(float val) {
            rightSubtitleSize = val;
            return this;
        }

        public Builder setLeftIconRes(int val) {
            leftIconRes = val;
            return this;
        }

        public Builder setRightIconRes(int val) {
            rightIconRes = val;
            return this;
        }

        public Builder setLeftSubtitleClickListener(View.OnClickListener val) {
            leftSubtitleClickListener = val;
            return this;
        }

        public Builder setRightSubtitleClickListener(View.OnClickListener val) {
            rightSubtitleClickListener = val;
            return this;
        }

        public CustomTitle build() {
            return new CustomTitle(this,activity);
        }
    }
}
