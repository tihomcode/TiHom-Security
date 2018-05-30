package com.tihom.secutity.core.social.qq.connect;

import com.tihom.secutity.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ连接工厂
 * @author TiHom
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    public QQConnectionFactory(String providerId,String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
