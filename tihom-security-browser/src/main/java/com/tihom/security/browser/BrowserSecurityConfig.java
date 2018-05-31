package com.tihom.security.browser;

import com.tihom.secutity.core.authentication.AbstractChannelSecurityConfig;
import com.tihom.secutity.core.authorize.AuthorizeConfigManager;
import com.tihom.secutity.core.properties.SecurityConstants;
import com.tihom.secutity.core.properties.SecurityProperties;
import com.tihom.secutity.core.validate.code.ValidateCodeSecurityConfig;
import com.tihom.secutity.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;


/**
 * 浏览器环境下安全配置主类
 * @author TiHom
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer tihomSocialSecurityConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //这是密码登录相关的配置
        applyPasswordAuthenticationConfig(http);


        http.apply(validateCodeSecurityConfig)
                .and()
            //短信验证相关的配置
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            //apply的作用就是往当前的过滤链上加过滤器,过滤器会拦截某些特定的请求,收到请求后引导用户去做社交登录
            .apply(tihomSocialSecurityConfig)
                .and()
            //浏览器特有的配置
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)  //失效时跳转的地址
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) //这是新session把旧session踢下去,类似游戏中的挤下线
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())  //新session无法踢下旧session,已经登录了就不能再另外登录了
                .expiredSessionStrategy(sessionInformationExpiredStrategy) //过期的session处理策略
                .and()
                .and()
            .logout()
                .logoutUrl("/signOut")
//                .logoutSuccessUrl("/tihom-logout.html")  //配了Handler就不能配Url,两者互斥
                .logoutSuccessHandler(logoutSuccessHandler) //退出成功的处理
                .deleteCookies("JSESSIONID")  //自定义要删除的cookie
                .and()
            .csrf().disable();  //防护的功能关闭

        authorizeConfigManager.config(http.authorizeRequests());
    }

    /**
     * 记住我功能的token存取器配置
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}