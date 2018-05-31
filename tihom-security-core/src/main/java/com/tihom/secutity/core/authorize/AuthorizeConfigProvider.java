package com.tihom.secutity.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置提供器,各个模块和业务系统可以通过实现此接口向系统添加授权配置
 * @author TiHom
 */
public interface AuthorizeConfigProvider {

    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
