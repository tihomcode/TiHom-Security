package com.tihom.secutity.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * SocialAuthenticationFilter后处理器,用于在不同环境下个性化社交登录的配置
 * @author TiHom
 */
public interface SocialAuthenticationFilterPostProcessor {
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
