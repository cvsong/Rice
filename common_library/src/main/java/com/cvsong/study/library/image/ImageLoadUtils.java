package com.cvsong.study.library.image;

import android.widget.ImageView;

/**
 * 图片加载工具类
 * Created by chenweisong on 2018/5/17.
 */

public class ImageLoadUtils {


    private static ImageLoadUtils imageLoadUtils;

    private IImageLoadManager imageLoadManager = new GlideImageManagerLoadManager();

    public static ImageLoadUtils getInstance() {

        if (imageLoadUtils == null) {
            synchronized (ImageLoadUtils.class) {
                if (imageLoadUtils == null) {
                    imageLoadUtils = new ImageLoadUtils();
                }
            }
        }
        return imageLoadUtils;
    }


    /**
     * 加载图片
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadImage(String imgUrl, ImageView imageView) {
        imageLoadManager.loadImage(imgUrl, imageView);
    }


    /**
     * 设置图片加载管理器
     *
     * @param manager
     */
    public void setImageLoadManager(IImageLoadManager manager) {
        imageLoadManager = manager;
    }


}
