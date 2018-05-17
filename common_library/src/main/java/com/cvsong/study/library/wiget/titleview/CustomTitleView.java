package com.cvsong.study.library.wiget.titleview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvsong.study.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义标题栏控件
 * Created by chenweisong on 2018/5/17.
 */

public class CustomTitleView extends FrameLayout implements ICustomTitleView {

    private RelativeLayout viewTitle;
    private RelativeLayout subtitleLeft;
    private ImageView ivSubtitleLeft;
    private TextView tvSubtitleLeft;
    private RelativeLayout subtitleRight;
    private ImageView ivSubtitleRight;
    private TextView tvSubtitleRight;
    private Activity activity;
    private TextView tvTitle;

    public CustomTitleView(@NonNull Context context) {
        this(context, null);
    }

    public CustomTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);//View初始化
    }

    /**
     * View初始化
     *
     * @param context
     */
    private void initView(Context context) {

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

        LayoutInflater.from(context).inflate(R.layout.wiget_title_view, this);



        /*整个标题栏*/
        viewTitle = findViewById(R.id.view_title);

        /*主标题*/
        tvTitle = findViewById(R.id.tv_title_name);

        /*左边副标题*/
        subtitleLeft = findViewById(R.id.rl_subtitle_left);
        ivSubtitleLeft = findViewById(R.id.iv_subtitle_left);
        tvSubtitleLeft = findViewById(R.id.tv_subtitle_left);


        /*右边副标题*/
        subtitleRight = findViewById(R.id.rl_subtitle_right);
        ivSubtitleRight = findViewById(R.id.iv_subtitle_right);
        tvSubtitleRight = findViewById(R.id.tv_subtitle_right);


    }


    /******************针对标题栏背景颜色的设置******************/

    @Override
    public void setTitleBackgroundColor(int backgroundColor) {
        setTitleBackgroundColor(backgroundColor, backgroundColor);
    }

    @Override
    public void setTitleBackgroundColor(int backgroundColor, int statusBarColor) {
        if (viewTitle == null) {
            return;
        }
        viewTitle.setBackgroundColor(backgroundColor);//设置标题栏背景颜色

        if (activity == null) {
            return;
        }

        StatusBarUtil.setColor(activity, statusBarColor);
    }

    /******************针对主标题的设置******************/

    @Override
    public void setTitleText(CharSequence text) {
        if (tvTitle == null) {
            return;
        }
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(text);
    }

    @Override
    public void setTitleText(CharSequence text, int textColor) {
        if (tvTitle == null) {
            return;
        }
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(text);
        tvTitle.setTextColor(textColor);
    }

    @Override
    public void setTitleText(CharSequence text, int textColor, int spTextSize) {
        if (tvTitle == null) {
            return;
        }
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(text);
        tvTitle.setTextColor(textColor);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spTextSize);
    }

    @Override
    public void setTitleTextVisibility(int visibility) {
        if (tvTitle == null) {
            return;
        }
        tvTitle.setVisibility(visibility);
    }

    /******************针对左边副标题的设置******************/
    @Override
    public void setLeftSubtitleText(CharSequence text) {
        if (tvSubtitleLeft == null) {
            return;
        }
        tvSubtitleLeft.setVisibility(VISIBLE);
        tvSubtitleLeft.setText(text);

    }

    @Override
    public void setLeftSubtitleText(CharSequence text, int textColor) {
        if (tvSubtitleLeft == null) {
            return;
        }
        tvSubtitleLeft.setVisibility(VISIBLE);
        tvSubtitleLeft.setText(text);
        tvSubtitleLeft.setTextColor(textColor);

    }

    @Override
    public void setLeftSubtitleText(CharSequence text, int textColor, int spTextSize) {
        if (tvSubtitleLeft == null) {
            return;
        }
        tvSubtitleLeft.setVisibility(VISIBLE);
        tvSubtitleLeft.setText(text);
        tvSubtitleLeft.setTextColor(textColor);
        tvSubtitleLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, spTextSize);
    }

    @Override
    public void setLeftSubTitleClickListener(OnClickListener listener) {
        if (subtitleLeft == null) {
            return;
        }
        subtitleLeft.setOnClickListener(listener);
    }

    @Override
    public void setLeftSubTitleVisibility(int visibility) {
        if (subtitleLeft==null) {
            return;
        }
        subtitleLeft.setVisibility(visibility);
    }


    /******************针对右边副标题的设置******************/

    @Override
    public void setRightSubtitleText(CharSequence text) {
        if (tvSubtitleRight == null) {
            return;
        }
        tvSubtitleRight.setVisibility(VISIBLE);
        tvSubtitleRight.setText(text);
    }

    @Override
    public void setRightSubtitleText(CharSequence text, int textColor) {
        if (tvSubtitleRight == null) {
            return;
        }
        tvSubtitleRight.setVisibility(VISIBLE);
        tvSubtitleRight.setText(text);
        tvSubtitleRight.setTextColor(textColor);
    }

    @Override
    public void setRightSubtitleText(CharSequence text, int textColor, int spTextSize) {
        if (tvSubtitleRight == null) {
            return;
        }
        tvSubtitleRight.setVisibility(VISIBLE);
        tvSubtitleRight.setText(text);
        tvSubtitleRight.setTextColor(textColor);
        tvSubtitleRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, spTextSize);
    }

    @Override
    public void setRightSubTitleClickListener(OnClickListener listener) {
        if (subtitleRight == null) {
            return;
        }
        subtitleRight.setOnClickListener(listener);
    }

    @Override
    public void setRightSubTitleVisibility(int visibility) {
        if (subtitleRight==null) {
            return;
        }
        subtitleRight.setVisibility(visibility);
    }


    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

}
