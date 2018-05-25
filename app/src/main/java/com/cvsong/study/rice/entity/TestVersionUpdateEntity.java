package com.cvsong.study.rice.entity;

/**
 * 版本更新测试实体类
 * Created by chenweisong on 2018/5/24.
 */

public class TestVersionUpdateEntity {

    private int versionCode;
    private String updateDesc;
    private String downloadUrl;
    private boolean haveToUpdate;

    public boolean isHaveToUpdate() {
        return haveToUpdate;
    }

    public void setHaveToUpdate(boolean haveToUpdate) {
        this.haveToUpdate = haveToUpdate;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateDesc() {
        return updateDesc == null ? "" : updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getDownloadUrl() {
        return downloadUrl == null ? "" : downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
