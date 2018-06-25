package com.cvsong.study.library.wiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;


import com.cvsong.study.library.R;

import java.util.Locale;

/**
 * 自定义倒计时按钮控件
 */

public class CountDownButton extends android.support.v7.widget.AppCompatButton {

    /**
     * 默认倒计时时间间隔1s
     */
    private static final long DEFAULT_INTERVAL = 1000;
    /**
     * 默认倒计时总时长60s
     */
    private static final long DEFAULT_COUNT = 60 * 1000;
    /**
     * 默认倒计时文字格式(显示秒数)
     */
    private static final String DEFAULT_COUNT_FORMAT = "%ds";
    /**
     * 默认按钮文字 {@link #getText()}
     */
    private String defaultText;

    /**
     * 倒计时总时长，单位为毫秒
     */
    private long count;
    /**
     * 时间间隔，单位为毫秒
     */
    private long interval;
    /**
     * 倒计时文字格式
     */
    private String countDownStringFormat;
    /**
     * 倒计时是否可用
     */
    private boolean enableCountDown = true;

    /**
     * 倒计时管理器
     */
    private CountDownTimer countDownTimerManager;

    /**
     * 是否正在执行倒计时
     */
    private boolean isCountDownNow;

    public CountDownButton(Context context) {
        super(context);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        // 获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton);
        //倒计时文字格式
        countDownStringFormat = typedArray.getString(R.styleable.CountDownButton_countDownFormat);
        if (countDownStringFormat==null) {
            countDownStringFormat=DEFAULT_COUNT_FORMAT;
        }
        //倒计时的总时间, 单位ms
        count = (int) typedArray.getFloat(R.styleable.CountDownButton_countDown, DEFAULT_COUNT);
        //倒计时的间隔时间, 单位ms
        interval = (int) typedArray.getFloat(R.styleable.CountDownButton_countDownInterval, DEFAULT_INTERVAL);
        //倒计时是否可用
        enableCountDown = (count > interval) && typedArray.getBoolean(R.styleable.CountDownButton_enableCountDown, true);
        typedArray.recycle();

        initCountDownTimer();//初始化倒计时

    }

    /**
     * 初始化倒计时
     */
    private void initCountDownTimer() {
        // 初始化倒计时Timer
        if (countDownTimerManager == null) {
            countDownTimerManager = new CountDownTimer(count, interval) {
                @Override
                public void onTick(long millisUntilFinished) {//当前任务每完成一次倒计时间隔时间时回调
                    setText(String.format(Locale.CHINA, countDownStringFormat, millisUntilFinished / 1000));
                    if (listener != null) {
                        listener.onTick(millisUntilFinished);//倒计时中
                    }
                }

                @Override
                public void onFinish() {//当前任务完成的时候回调
                    isCountDownNow = false;
                    setEnabled(true);
                    setText(defaultText);
                    if (listener != null) {
                        listener.onCountDownFinish();//倒计时结束
                    }

                }
            };
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Rect rect = new Rect();
                this.getGlobalVisibleRect(rect);
                if (enableCountDown && rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    defaultText = getText().toString();
                    // 设置按钮不可点击
                    setEnabled(false);
                    // 开始倒计时
                    countDownTimerManager.start();
                    isCountDownNow = true;
                    if (listener != null) {
                        listener.onStartCountDown();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setEnableCountDown(boolean enableCountDown) {
        this.enableCountDown = (count > interval) && enableCountDown;
    }

    /**
     * 开始倒计时
     */
    public void startCountDown() {
        if (enableCountDown) {
            this.defaultText = getText().toString();
            // 设置按钮不可点击
            setEnabled(false);
            // 开始倒计时
            countDownTimerManager.start();
            isCountDownNow = true;
        }
    }


    /**
     * 移除倒计时
     */
    public void removeCountDown() {
        if (countDownTimerManager != null) {
            countDownTimerManager.cancel();
        }
        isCountDownNow = false;
        setText(defaultText);
        setEnabled(true);
    }


    /**
     * 是否正在执行倒计时
     *
     * @return 倒计时期间返回true否则返回false
     */
    public boolean isCountDownNow() {
        return isCountDownNow;
    }

    /**
     * 设置倒计时数据
     *
     * @param count           时长
     * @param interval        间隔
     * @param countDownFormat 文字格式
     */
    public void setCountDown(long count, long interval, String countDownFormat) {
        this.count = count;
        this.countDownStringFormat = countDownFormat;
        this.interval = interval;
        setEnableCountDown(true);
    }


    @Override
    public void setEnabled(boolean enabled) {
        if (isCountDownNow()) {
            return;
        }
        super.setEnabled(enabled);
        setClickable(enabled);
    }


    private OnCountDownListener listener;

    /**
     * 倒计时监听
     */
    public interface OnCountDownListener {
        void onStartCountDown();//倒计时开始

        void onTick(long millisUntilFinished);//倒计时中

        void onCountDownFinish();//倒计时结束

    }


    /**
     * 设置倒计时监听
     *
     * @param listener
     */
    public void setOnCountDownListener(OnCountDownListener listener) {
        this.listener = listener;
    }


}
