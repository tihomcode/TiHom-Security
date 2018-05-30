package com.tihom.secutity.core.social.qq.config;

import com.tihom.secutity.core.properties.QQProperties;
import com.tihom.secutity.core.properties.SecurityProperties;
import com.tihom.secutity.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author TiHom
 */
@Configuration
@ConditionalOnProperty(prefix = "tihom.security.social.qq",name = "aqq-id")  //如果在我的系统中app-id被配置了值了,下面的配置才生效
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(),qqConfig.getAppId(),qqConfig.getAppSecret());
    }
}
