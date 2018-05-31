package com.tihom.secutity.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * 微信登录配置项
 * @author TiHom
 */
public class WeixinProperties extends SocialProperties {

    /**
     * 第三方id,用来决定发起第三方登录的url,默认是weixin
     */
    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
