package com.tihom.secutity.core;

import com.tihom.secutity.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author TiHom
 */

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)   //配置读取器，让这些类生效
public class SecurityCoreConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
