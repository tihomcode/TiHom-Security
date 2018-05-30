package com.tihom.secutity.core.properties;

/**
 * @author TiHom
 */
public class OAuth2Properties {

    private String jwtSigningKey = "tihom";

    private OAuth2ClientProperties[] clients = {};

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
