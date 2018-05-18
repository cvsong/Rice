package com.cvsong.study.library.image;

import android.widget.ImageView;

/**
 * 图片加载接口
 * Created by chenweisong on 2018/5/17.
 */

public interface IImageLoadManager {

    void loadImage(String imgUrl, ImageView imageView);

}
