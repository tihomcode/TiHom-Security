package com.tihom.security;

import com.tihom.secutity.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author TiHom
 */
@Component
@Order(Integer.MAX_VALUE)  //确保在权限模块配置读取完之后再读取
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //任何请求
        config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
        return true;
    }
}
