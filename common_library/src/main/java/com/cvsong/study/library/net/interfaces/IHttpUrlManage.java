package com.cvsong.study.library.net.interfaces;

/**
 * 网络请求管理类接口
 */
public interface IHttpUrlManage {


    /****************常量配置*****************/
    public static final int FALSE = 0;
    public static final int TRUE = 1;

    public static final int HTTP_NORMAL = 0;
    public static final int HTTP_SPECIAL = 1;

    public static final int NO = 0;
    public static final int YES = 1;







    /**
     * 获取URL唯一性标识
     *
     * @return URL描述
     */
    int getUrlId();


    /**
     * 获取URL描述信息
     *
     * @return URL描述
     */
    String getUrlDesc();


    /**
     * 获取url
     *
     * @return 请求的URL
     */
    String getUrl();


    /**
     * 是否需要缓存
     *
     * @return true:需要;false:不需要
     */
    boolean isNeedCache();


    /**
     * 获取请求URL类型
     *
     * @return NORMAL:统一拼接主机名和端口号;SPECIAL:特殊情况,不需要拼接
     */
    int getRequestUrlType();


    /**
     * 获取响应结果类型
     *
     * @return NORMAL:统一解析外层json数据;SPECIAL:单独解析外层Json数据
     */
    int getResponseDataType();


    /**
     * 是否需要加密请求数据
     *
     * @return true:需要;false:不需要
     */
    boolean isNeedEncryptRequestData();

    /**
     * 是否需要解密响应数据
     *
     * @return true:需要;false:不需要
     */
    boolean isNeedDecryptResponseData();


}
