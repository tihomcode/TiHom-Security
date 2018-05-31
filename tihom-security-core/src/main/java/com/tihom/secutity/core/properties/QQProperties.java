package com.tihom.secutity.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * QQ登录配置项
 * @author TiHom
 */
public class QQProperties extends SocialProperties {

    /**
     * 第三方id,用来决定发起第三方登录的url,默认是qq
     */
    private String providerId = "qq";


    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
