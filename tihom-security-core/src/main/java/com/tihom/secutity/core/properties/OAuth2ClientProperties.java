package com.tihom.secutity.core.properties;

/**
 * @author TiHom
 */
public class OAuth2ClientProperties {
    private String clientId;

    private String clientSecret;

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
