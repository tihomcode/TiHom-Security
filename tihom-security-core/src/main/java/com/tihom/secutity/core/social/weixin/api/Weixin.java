package com.tihom.secutity.core.social.weixin.api;

/**
 * @author TiHom
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}
