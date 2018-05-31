package com.tihom.secutity.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认的授权配置管理器,这个管理器的作用是把我系统中所有的provider收集起来
 * @author TiHom
 */
@Component
public class TiHomAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders){
            authorizeConfigProvider.config(config);
        }
        //先注释避免与DemoAuthorizeConfigProvider中的anyRequest冲突
//        config.anyRequest().authenticated();
    }
}
