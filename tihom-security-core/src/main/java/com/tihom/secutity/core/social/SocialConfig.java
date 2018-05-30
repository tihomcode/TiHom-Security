package com.tihom.secutity.core.social;

import com.tihom.secutity.core.properties.SecurityProperties;
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
 * 社交配置的适配器
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

    @Bean
    public SpringSocialConfigurer tihomSocialSecurityConfig(){
        TihomSpringSocialConfigurer configurer = new TihomSpringSocialConfigurer
                (securityProperties.getSocial().getFilterProcessesUrl());
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        //配置后处理器
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)){};
    }
}
