package com.cvsong.study.rice.entity;

import lombok.Data;

/**
 * 版本更新测试实体类
 * Created by chenweisong on 2018/5/24.
 */
@Data
public class TestVersionUpdateEntity {

    private int versionCode;
    private String updateDesc;
    private String downloadUrl;
    private boolean haveToUpdate;

}
