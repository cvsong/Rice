package com.cvsong.study.library.net.interfaces;

/**
 * 网络请求管理类接口
 */
public interface IHttpUrlManage {

    /**
     * 获取urltype
     * @return
     */
    public String getUrlType();

    /**
     * 获取url
     * @return
     */
    public String getUrl();

    /**
     * credit的格式比较特殊所以这里需要特殊处理
     * @return
     */
    public boolean getCreditJSON();

    /**
     * 是否需要缓存
     * @return
     */
    public boolean isNeedCache();

}
