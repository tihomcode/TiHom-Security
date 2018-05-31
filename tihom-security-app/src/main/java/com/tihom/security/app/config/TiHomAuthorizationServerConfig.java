package com.tihom.security.app.config;

import com.tihom.secutity.core.properties.OAuth2ClientProperties;
import com.tihom.secutity.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 * @author TiHom
 */

@Configuration
@EnableAuthorizationServer //加上这句注解就已经实现了认证服务器
public class TiHomAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    /**
     * 认证及token配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);

        if(jwtAccessTokenConverter!=null && jwtTokenEnhancer!=null){
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();

            //通过TokenEnhancerChain增强器链将jwtAccessTokenConverter(转换成jwt)和jwtTokenEnhancer(往里面加内容加信息)连起来
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);

            endpoints
                //用enhancerChain来配置endpoints这个端点
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * tokenKey的访问权限表达式配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
    }

    /**
     * 客户端配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //覆盖这个方法后,在application.properties中的配置都是无效的
        //指定client_id
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        //判断我们配置的client信息是否已经配了
        if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
            //如果配了的话,在里面遍历出来
            for(OAuth2ClientProperties config : securityProperties.getOauth2().getClients()){
                //在循环里设置
                builder.withClient(config.getClientId())
                        //指定id的密码
                        .secret(config.getClientSecret())
                        //发出去的令牌的有效时间
				        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        //针对tihom的这些应用所能支持的授权模式是哪些,一共五种方式
                        .authorizedGrantTypes("refresh_token","password")
                        //关于refreshToken的失效时间
                        .refreshTokenValiditySeconds(2592000)
                        //OAuth中的权限,这里配置后请求中就不会带上scope参数
                        .scopes("all","read","write");
            }
        }
    }
}
