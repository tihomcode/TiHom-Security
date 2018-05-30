package com.tihom.secutity.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author TiHom
 */
public interface SocialAuthenticationFilterPostProcessor {
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
