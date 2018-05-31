package com.tihom.secutity.core.properties;

/**
 * 浏览器环境配置项
 * @author TiHom
 */
public class BrowserProperties {

    /**
     * session管理配置项
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
     */
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    /**
     * 社交登录,如果需要用户注册,跳转的页面
     */
    private String signUpUrl = "/tihom-signUp.html";

    /**
     * 退出成功时跳转的url,如果配置了,则跳到指定的url,如果没配置,则返回json数据
     */
    private String signOutUrl;

    /**
     * 登录响应的方式,默认是json
     */
    private LoginType loginType = LoginType.JSON;

    /**
     * '记住我'功能的有效时间
     */
    private int rememberMeSeconds = 604800;

    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
     * 只在signInResponseType为REDIRECT时生效
     */
    private String signInSuccessUrl;

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

    public String getSignInSuccessUrl() {
        return signInSuccessUrl;
    }

    public void setSignInSuccessUrl(String signInSuccessUrl) {
        this.signInSuccessUrl = signInSuccessUrl;
    }
}
