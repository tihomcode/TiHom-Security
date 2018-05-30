package com.tihom.secutity.core.authentication.mobile;

import com.tihom.secutity.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.tihom.secutity.core.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author TiHom
 */

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler tihomAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler tihomAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        //配置Filter
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        //配置AuthenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        //配置
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(tihomAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(tihomAuthenticationFailureHandler);
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        //配置smsCodeAuthenticationProvider所调用的UserDetailsService
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        //将自定义的AuthenticationProvider添加到AuthenticationManager所管理的Provider集合里面去
        builder.authenticationProvider(smsCodeAuthenticationProvider)
                //将过滤器添加到用户名密码验证过滤器的后面就行
                .addFilterAfter(smsCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }
}
