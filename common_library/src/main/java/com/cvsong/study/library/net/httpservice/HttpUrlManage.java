package com.cvsong.study.library.net.httpservice;


import com.cvsong.study.library.net.interfaces.IHttpUrlManage;

/**
 * 网络请求URL组装类
 */
public enum HttpUrlManage implements IHttpUrlManage {
    //公共部分
    WMS_SYSTEM_LOGIN("登录", URLDomain.SYSTEM_LOGIN),//登录



    ;


    private String urlType;
    private URLDomain urlDomain;

    HttpUrlManage(String urlType, URLDomain urlDomain) {
        this.urlType = urlType;
        this.urlDomain = urlDomain;
    }

    /**
     * 获取urltype
     *
     * @return
     */
    public String getUrlType() {
        return urlType;
    }

    /**
     * 获取url
     *
     * @return
     */
    public String getUrl() {
        return urlDomain.getDomian();
    }

    @Override
    public boolean getCreditJSON() {
        return true;
    }

    /**
     * 是否需要缓存
     *
     * @return
     */
    @Override
    public boolean isNeedCache() {
        return urlDomain.getNeedCache();
    }

    /**
     * 是否需要缓存数据
     ***/
    public enum InterfaceNeedCache {
        NO,//不需要
        YES,//需要
    }

    /**
     * 组装URL
     **/
    public enum URLDomain {
        //公共模块
        SYSTEM_LOGIN("http://%s%s/SysAdmin/rpc/login", InterfaceNeedCache.NO),//登录




        ;


        private String urlFormat;
        private InterfaceNeedCache needCache;

        URLDomain(String urlFormat, InterfaceNeedCache needCache) {
            this.urlFormat = urlFormat;
            this.needCache = needCache;
        }

        public String getDomian() {
            return getUrl(urlFormat);
        }


        public boolean getNeedCache() {
            if (InterfaceNeedCache.YES.toString().equals(needCache.toString())) {
                return true;
            } else if (InterfaceNeedCache.NO.toString().equals(needCache.toString())) {
                return false;
            }
            return false;
        }

        /**
         * 组装网络请求URL
         */
        private static String getUrl(String urlFormat) {

            return String.format(urlFormat, HttpConstants.BASE_URL, HttpConstants.URL_PORT);
        }
    }
}
