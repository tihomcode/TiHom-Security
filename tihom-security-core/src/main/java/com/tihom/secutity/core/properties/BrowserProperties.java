package com.tihom.secutity.core.properties;

/**
 * @author TiHom
 */
public class BrowserProperties {

    private SessionProperties session = new SessionProperties();

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private String signUpUrl = "/tihom-signUp.html";

    private String signOutUrl;

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 604800;

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
