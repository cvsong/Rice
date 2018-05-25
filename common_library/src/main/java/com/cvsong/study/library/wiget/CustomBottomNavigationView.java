package com.cvsong.study.library.wiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvsong.study.library.R;


/**
 * 自定义底部导航栏
 * Created by chenweisong on 2017/4/11.
 */

public class CustomBottomNavigationView extends RelativeLayout {

    private ImageView ivIcon;
    private TextView tvText;

    public CustomBottomNavigationView(Context context) {
        this(context, null);
    }

    public CustomBottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.wiget_custom_bottom_navigation, this, true);
        //图标文本
        ivIcon = findViewById(R.id.iv_icon);
        //底部文字文字
        tvText = findViewById(R.id.tv_text);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomBottomNavigationView);
        if (attributes == null) {
            return;
        }

        Drawable topIcon = attributes.getDrawable(R.styleable.CustomBottomNavigationView_top_icon);
        ivIcon.setImageDrawable(topIcon);//顶部图标

        String bottomText = attributes.getString(R.styleable.CustomBottomNavigationView_bottom_text);
        tvText.setText(bottomText);//设置底部文本


        attributes.recycle();

    }

    /**
     * 条目点击监听
     *
     * @param listener
     */
    public void setOnViewClickListener(OnClickListener listener) {
        setOnClickListener(listener);
    }


}
