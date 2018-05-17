package com.cvsong.study.library.util;

import android.text.TextUtils;

/**
 * 表情符号过滤工具类
 */
public class EmojiFilterUtils {
    /**
     * 去除emoji表情
     * [\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]
     *
     * @param source 原字符串
     * @return 去除后的字符串
     */
    public static String filterEmoji(String source) {
        if (!TextUtils.isEmpty(source)) {
            return source.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d" +
                    "\\udfff]|[\\u2600-\\u27ff]", "");
        } else {
            return "";
        }
    }
}
