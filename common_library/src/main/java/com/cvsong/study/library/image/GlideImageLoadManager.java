package com.cvsong.study.library.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cvsong.study.library.util.utilcode.util.Utils;

/**
 * Gilde-图片加载管理类
 * Created by chenweisong on 2018/5/17.
 */

public class GlideImageLoadManager implements IImageLoadManager {


    @Override
    public void loadImage(String imgUrl, ImageView imageView) {

        Glide.with(Utils.getApp()).load(imgUrl).into(imageView);


    }
}
