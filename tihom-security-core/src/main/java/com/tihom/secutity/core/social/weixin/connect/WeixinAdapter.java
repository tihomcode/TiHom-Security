package com.tihom.secutity.core.social.weixin.connect;

import com.tihom.secutity.core.social.weixin.api.Weixin;
import com.tihom.secutity.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author TiHom
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    //这里需要引入openId,下面getUserInfo时需要
    private String openId;

    public WeixinAdapter(){}

    public WeixinAdapter(String openId){
        this.openId = openId;
    }

    /**
     * 测试微信与应用之间是否可用的,这里改为true默认为可用
     * @param api
     * @return
     */
    @Override
    public boolean test(Weixin api) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo userInfo = api.getUserInfo(openId);
        //微信昵称
        values.setDisplayName(userInfo.getNickname());
        //微信头像
        values.setImageUrl(userInfo.getHeadimgurl());
        values.setProviderUserId(userInfo.getOpenid());
        //没有个人主页
        values.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    @Override
    public void updateStatus(Weixin api, String message) {

    }
}
