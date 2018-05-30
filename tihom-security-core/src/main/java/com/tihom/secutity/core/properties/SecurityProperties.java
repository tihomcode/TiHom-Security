package com.tihom.secutity.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author TiHom
 */

@ConfigurationProperties(prefix = "tihom.security")  //意思是这个类会读取整个系统中以tihom.security开头的配置
public class SecurityProperties {

    //browser的属性都会配置在这个BrowserProperties中
    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

    public BrowserProperties getBrowser(){
        return browser;
    }

    public void setBrowser(BrowserProperties browser){
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth) {
        this.oauth2 = oauth;
    }
}
