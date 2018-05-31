package com.tihom.secutity.core.properties;

/**
 * 认证服务器注册的第三方应用配置项
 * @author TiHom
 */
public class OAuth2ClientProperties {
    /**
     * 第三方应用appId
     */
    private String clientId;

    /**
     * 第三方应用appSecret
     */
    private String clientSecret;

    /**
     * 针对此应用发出的token的有效时间
     */
    private int accessTokenValiditySeconds = 7200;

    //具体使用时可以在这里继续加上想要配置的属性

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}
