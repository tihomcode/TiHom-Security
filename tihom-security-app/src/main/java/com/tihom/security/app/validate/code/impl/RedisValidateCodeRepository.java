package com.tihom.security.app.validate.code.impl;

import com.tihom.secutity.core.validate.code.ValidateCode;
import com.tihom.secutity.core.validate.code.ValidateCodeException;
import com.tihom.secutity.core.validate.code.ValidateCodeType;
import com.tihom.secutity.core.validate.code.sms.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * redis的验证码策略
 * @author TiHom
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        //设置30分钟的超时时间
        redisTemplate.opsForValue().set(buildKey(request,validateCodeType),code,30,TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        Object value = redisTemplate.opsForValue().get(buildKey(request,validateCodeType));
        if(value==null){
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        redisTemplate.delete(buildKey(request,codeType));
    }

    private String buildKey(ServletWebRequest request,ValidateCodeType type){
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase()+":"+deviceId;
    }
}
