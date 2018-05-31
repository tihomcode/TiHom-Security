package com.tihom.secutity.core.social;

import com.tihom.secutity.core.properties.SecurityProperties;
import com.tihom.secutity.core.social.support.SocialAuthenticationFilterPostProcessor;
import com.tihom.secutity.core.social.support.TihomSpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登录配置主类
 * @author TiHom
 */

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)  //不一定会提供,浏览器情况下就不需要做处理
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository
                (dataSource,/*连接工厂的定位器*/connectionFactoryLocator,/*这里是对加入数据库的数据进行加解密的*/Encryptors.noOpText());
        repository.setTablePrefix("tihom_");
        if(connectionSignUp!=null){
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配置类,供浏览器或app模块引入设计登录配置用
     */
    @Bean
    public SpringSocialConfigurer tihomSocialSecurityConfig(){
        TihomSpringSocialConfigurer configurer = new TihomSpringSocialConfigurer
                (securityProperties.getSocial().getFilterProcessesUrl());
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        //配置后处理器
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    /**
     * 用来处理注册流程的工具类
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)){};
    }
}
