package com.tihom.security.app;

import com.tihom.security.app.jwt.TiHomJwtTokenEnhancer;
import com.tihom.secutity.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author TiHom
 */
@Configuration
public class TokenStoreConfig {
    /**
     * 使用redis存储token的配置，只有在tihom.security.oauth2.tokenStore配置为redis时生效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "tihom.security.oauth2", name = "tokenStore", havingValue = "redis")
    public static class RedisConfig {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "tihom.security.oauth2",name = "storeType",havingValue = "jwt",matchIfMissing = true)
    //避免TokenStore冲突
    //表示要检查的属性是tihom.security.oauth2.storeType,当这个属性的值为"jwt"时这个内部类里面的配置生效,如果在配置文件中没有配这个属性的时候
    //我认为你是匹配的,如果我在配置文件里没写这个属性项,底下整个都是生效的
    public static class JwtTokenConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore(){
            //JwtTokenStore用来读取的,并不管token的生成
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 转换成JWT
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //set密钥
            accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            //访问令牌转换器
            return accessTokenConverter;
        }

        /**
         * JWT增强器,往里面加内容加信息
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        //在不同系统里面可能处理是不同的,这里只是提供默认的,系统可以通过自己加入一个叫jwtTokenEnhancer的bean覆盖掉这个默认的逻辑
        public TokenEnhancer jwtTokenEnhancer(){
            return new TiHomJwtTokenEnhancer();
        }
    }
}
