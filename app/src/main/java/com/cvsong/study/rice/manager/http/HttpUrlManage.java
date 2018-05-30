package com.cvsong.study.rice.manager.http;


import android.support.annotation.IntDef;

import com.cvsong.study.library.net.httpservice.HttpConstants;
import com.cvsong.study.library.net.interfaces.IHttpUrlManage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 网络请求URL组装类
 */
public enum HttpUrlManage implements IHttpUrlManage {
    //公共部分
    RICE_SYSTEM_LOGIN(1, "登录", URLParameter.SYSTEM_LOGIN),//登录
    RICE_SYSTEM_VERSION_UPDATE(2, "版本更新", URLParameter.RICE_SYSTEM_VERSION_UPDATE) {},//版本更新


    ;


    /**
     * URL参数
     **/
    public enum URLParameter {
        //公共部分
        SYSTEM_LOGIN("http://%s%s/SysAdmin/rpc/login", NO, HTTP_NORMAL, HTTP_NORMAL, NO, NO),//登录
        RICE_SYSTEM_VERSION_UPDATE("http://%s%s/SysAdmin/rpc/login", NO, HTTP_NORMAL, HTTP_NORMAL, NO, NO),//版本更新


        ;


        private String urlContent;
        private int requestNeedCache;//是否需要缓存
        private final int requestUrlType;//网络请求URL类型
        private final int responseDataType;//请求结果类型
        private final int encryptRequestData;//是否加密请求数据
        private final int decryptResponseData;//响应数据是否需要解密

        URLParameter(String urlContent, @RequestNeedCache int requestNeedCache,
                     @RequestUrlType int requestUrlType,
                     @ResponseDataType int responseDataType,
                     @EncryptRequestData int encryptRequestData,
                     @DecryptResponseData int decryptResponseData) {

            this.urlContent = urlContent;
            this.requestUrlType = requestUrlType;
            this.requestNeedCache = requestNeedCache;
            this.responseDataType = responseDataType;
            this.encryptRequestData = encryptRequestData;
            this.decryptResponseData = decryptResponseData;
        }

        //获取请求URL类型
        public int getRequestUrlType() {
            return requestUrlType;
        }

        //获取响应结果类型
        public int getResponseDataType() {
            return responseDataType;
        }

        //获取URL
        private String getUrl() {
            if (requestUrlType == HTTP_NORMAL) {
                return getNormalURL(urlContent);
            } else {
                return urlContent;
            }
        }

        //是否需要缓存数据
        private boolean getRequestIsNeedCache() {
            return requestNeedCache == TRUE;
        }

        //是否需要加密请求数据
        public boolean getIsNeedEncryptRequestData() {
            return encryptRequestData == TRUE;
        }

        //是否需要解密响应数据
        public boolean getIsNeedDecryptResponseData() {
            return decryptResponseData == TRUE;
        }

        //组装网络请求URL
        private String getNormalURL(String urlFormat) {
            return String.format(urlFormat, HttpConstants.BASE_URL, HttpConstants.URL_PORT);
        }

    }





    /**
     * 网络请求URL类型
     */
    @IntDef({HTTP_NORMAL, HTTP_SPECIAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestUrlType {
    }

    /**
     * 网络请求响应结果类型
     */
    @IntDef({HTTP_NORMAL, HTTP_SPECIAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseDataType {
    }


    /**
     * 请求是否需要进行缓存
     */
    @IntDef({NO, YES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestNeedCache {
    }

    /**
     * 请求数据是否需要加密
     */
    @IntDef({NO, YES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EncryptRequestData {
    }

    /**
     * 响应结果是否需要解密
     */
    @IntDef({NO, YES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DecryptResponseData {
    }


    private final int urlId;//URL唯一性标识
    private String urlDesc;
    private URLParameter urlParameter;

    HttpUrlManage(int urlId, String urlDesc, URLParameter urlParameter) {
        this.urlId = urlId;
        this.urlDesc = urlDesc;
        this.urlParameter = urlParameter;
    }


    /**
     * 获取URL唯一性标识
     */
    @Override
    public int getUrlId() {
        return urlId;
    }

    /**
     * 获取接口描述信息
     */
    @Override
    public String getUrlDesc() {
        return urlDesc;
    }


    /**
     * 获取url
     */
    @Override
    public String getUrl() {
        return urlParameter.getUrl();
    }


    /**
     * 是否需要缓存
     */
    @Override
    public boolean isNeedCache() {
        return urlParameter.getRequestIsNeedCache();
    }


    /**
     * 获取请求URL类型
     */
    @Override
    public int getRequestUrlType() {
        return urlParameter.getRequestUrlType();
    }


    /**
     * 获取响应结果类型
     */
    @Override
    public int getResponseDataType() {
        return urlParameter.getResponseDataType();
    }

    /**
     * 是否需要加密请求数据
     */
    @Override
    public boolean isNeedEncryptRequestData() {
        return urlParameter.getIsNeedEncryptRequestData();
    }

    /**
     * 是否需要解密响应数据
     */
    @Override
    public boolean isNeedDecryptResponseData() {
        return urlParameter.getIsNeedDecryptResponseData();
    }


}
