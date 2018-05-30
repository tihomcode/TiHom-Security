package com.tihom.secutity.core.social.qq.connect;

import com.tihom.secutity.core.social.qq.api.QQ;
import com.tihom.secutity.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author TiHom
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    //将用户导向认证服务器的url
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    //拿着授权码申请令牌时的url
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId,String appSecret) {
        //这是让QQ知道是哪个app在向它发请求,appId和appSecret两个属性是注册QQ互联时QQ提供的,后面两个url都是QQ互联提供的
        super(new QQOAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
