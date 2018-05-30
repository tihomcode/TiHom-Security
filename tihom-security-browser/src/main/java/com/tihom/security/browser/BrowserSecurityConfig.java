package com.tihom.security.browser;

import com.tihom.secutity.core.authentication.AbstractChannelSecurityConfig;
import com.tihom.secutity.core.properties.SecurityConstants;
import com.tihom.secutity.core.properties.SecurityProperties;
import com.tihom.secutity.core.validate.code.ValidateCodeSecurityConfig;
import com.tihom.secutity.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            .authorizeRequests()   //认证请求
                .antMatchers(
                    SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                    SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                    securityProperties.getBrowser().getLoginPage(),
                    SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                    securityProperties.getBrowser().getSignUpUrl(),
                    securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                    securityProperties.getBrowser().getSignOutUrl(),
                    "/user/regist")
                    .permitAll() //当我访问这个url的时候,我不需要身份认证就可以访问,其他的都需要认证
                .anyRequest()   //任何请求
                .authenticated()   //进行身份认证
                .and()
            .csrf().disable();  //防护的功能关闭
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
