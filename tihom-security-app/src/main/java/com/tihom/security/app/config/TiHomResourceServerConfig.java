package com.tihom.security.app.config;

import com.tihom.security.app.social.openid.OpenIdAuthenticationSecurityConfig;
import com.tihom.secutity.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.tihom.secutity.core.authorize.AuthorizeConfigManager;
import com.tihom.secutity.core.properties.SecurityConstants;
import com.tihom.secutity.core.properties.SecurityProperties;
import com.tihom.secutity.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 资源服务器配置
 * @author TiHom
 */

@Configuration
@EnableResourceServer
public class TiHomResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer tihomSocialSecurityConfig;

    @Autowired
    protected AuthenticationSuccessHandler tihomAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler tihomAuthenticationFailureHandler;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(tihomAuthenticationSuccessHandler)
                .failureHandler(tihomAuthenticationFailureHandler);

        http.apply(validateCodeSecurityConfig)
                .and()
                //短信验证相关的配置
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                //apply的作用就是往当前的过滤链上加过滤器,过滤器会拦截某些特定的请求,收到请求后引导用户去做社交登录
            .apply(tihomSocialSecurityConfig)
                .and()
            .apply(openIdAuthenticationSecurityConfig)
                .and()
            .csrf().disable();  //防护的功能关闭
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
