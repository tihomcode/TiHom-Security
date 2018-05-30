package com.tihom.secutity.core.social.qq.connect;

import com.tihom.secutity.core.social.qq.api.QQ;
import com.tihom.secutity.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ适配器
 * @author TiHom
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 测试当前QQapi是否可用
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 创建connection所需要的数据项
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        //QQ昵称
        values.setDisplayName(userInfo.getNickname());
        //QQ头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        //QQ个人主页(微博那些就会有)
        values.setProfileUrl(null);
        //服务商的用户id==openId
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
