package com.tihom.secutity.core.social.weixin.config;

import com.tihom.secutity.core.properties.SecurityProperties;
import com.tihom.secutity.core.properties.WeixinProperties;
import com.tihom.secutity.core.social.TiHomConnectView;
import com.tihom.secutity.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @author TiHom
 */
@Configuration
@ConditionalOnProperty(prefix = "tihom.security.social.weixin",name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinConfig.getProviderId(),
                weixinConfig.getAppId(),weixinConfig.getAppSecret());
    }

    /**
     * 绑定的视图
     */
    @Bean({"connect/wexinConnected","connect/weixinConnect"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView(){
        return new TiHomConnectView();
    }


}
