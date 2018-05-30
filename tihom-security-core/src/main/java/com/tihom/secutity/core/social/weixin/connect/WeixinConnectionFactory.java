package com.tihom.secutity.core.social.weixin.connect;

import com.tihom.secutity.core.social.weixin.api.Weixin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信连接工厂
 * @author TiHom
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {

    /**
     * @param providerId
     * @param appId
     * @param appSecret
     */
    public WeixinConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId,new WeixinServiceProvider(appId,appSecret),new WeixinAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的,所以在这里直接根据accessToken设置providerUserId即可,不用像QQ那样通过QQAdapter获取
     * @param accessGrant
     * @return
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WeixinAccessGrant){
            return ((WeixinAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }

    /**
     * 通过访问权限获取微信连接
     * @param accessGrant
     * @return
     */
    @Override
    public Connection<Weixin> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<Weixin>(getProviderId(),extractProviderUserId(accessGrant),
                accessGrant.getAccessToken(),accessGrant.getRefreshToken(),accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(),getApiAdapter(extractProviderUserId(accessGrant)));
    }


    @Override
    public Connection<Weixin> createConnection(ConnectionData data) {
        return new OAuth2Connection<Weixin>(data,getOAuth2ServiceProvider(),getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
        //多实例对象,不同的人openId不一样,每一个openId对应一个对象,qq这里每一次传回去都是同一个对象
        return new WeixinAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider(){
        return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
    }


}
