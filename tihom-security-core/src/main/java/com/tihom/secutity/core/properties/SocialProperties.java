package com.tihom.secutity.core.properties;

/**
 * 社交登录配置项
 * @author TiHom
 */
public class SocialProperties {

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();

    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
