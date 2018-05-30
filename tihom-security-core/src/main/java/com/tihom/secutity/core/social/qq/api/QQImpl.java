package com.tihom.secutity.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author TiHom
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{

    //QQ互联拿openId时发请求的路径
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    //QQ互联提供的获取用户信息的路径
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    //对应的应用程序编号即唯一标识
    private String appId;

    //用户在系统里的唯一标识
    private String openId;

    //工具类,将json串转换为对象
    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken,String appId){
        //我们把acessToken作为查询参数传递,父类默认使用的是放入请求头的方式传递参数
        super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        //将accessToken(令牌)拼接在url上
        String url = String.format(URL_GET_OPENID,accessToken);
        //发送http请求并返回callback对象callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String result = getRestTemplate().getForObject(url,String.class);

        System.out.println(result);
        //获取openId
        this.openId = StringUtils.substringBetween(result,"\"openid\":\"","\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        //将appId和openId放在url中
        String url = String.format(URL_GET_USERINFO,appId,openId);
        //发送请求获取用户信息
        String result = getRestTemplate().getForObject(url,String.class);
        System.out.println(result);
        QQUserInfo userInfo;
        try {
            //将json串转换为QQUserInfo对象
            userInfo = objectMapper.readValue(result,QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }
}
