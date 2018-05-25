package com.cvsong.study.library.wiget.titleview;

import android.support.annotation.ColorInt;
import android.view.View;

/**
 * 自定义标题栏接口
 */
public interface ICustomTitleView {





    /******************针对标题栏整体的设置******************/

    void setTitleBackgroundColor(@ColorInt int backgroundColor);

    void setTitleBackgroundColor(@ColorInt int backgroundColor, @ColorInt int statusBarColor);

    void setTitleVisibility(@CustomTitleView.Visibility int visibility);

    /******************针对主标题的设置******************/

    /**
     * 设置主标题文本
     *
     * @param text
     */
    void setTitleText(CharSequence text);

    void setTitleText(CharSequence text, @ColorInt int textColor);

    void setTitleText(CharSequence text, @ColorInt int textColor, int spTextSize);

    void setTitleTextVisibility(@CustomTitleView.Visibility int visibility);




    /******************针对左边副标题的设置******************/

    /**
     * 设置左副标题文本
     *
     * @param text
     */
    void setLeftSubtitleText(CharSequence text);

    void setLeftSubtitleText(CharSequence text, @ColorInt int textColor);

    void setLeftSubtitleText(CharSequence text, @ColorInt int textColor, int spTextSize);

    void setLeftSubTitleClickListener(View.OnClickListener listener);

    void setLeftSubTitleVisibility(@CustomTitleView.Visibility int visibility);


    /******************针对右边副标题的设置******************/

    /**
     * 设置右副标题文本
     *
     * @param text
     */
    void setRightSubtitleText(CharSequence text);

    void setRightSubtitleText(CharSequence text, @ColorInt int textColor);

    void setRightSubtitleText(CharSequence text, @ColorInt int textColor, int spTextSize);

    void setRightSubTitleClickListener(View.OnClickListener listener);

    void setRightSubTitleVisibility(@CustomTitleView.Visibility int visibility);

}
