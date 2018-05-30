package com.tihom.secutity.core.validate.code.sms;

/**
 * @author TiHom
 */
public interface SmsCodeSender {

    /**
     * @param mobile 在哪个手机发送
     * @param code 短信验证码是什么
     */
    void send(String mobile,String code);
}
