package com.tihom.security.app.social;

import com.tihom.security.app.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * app环境下替换providerSignInUtils，避免由于没有session导致读不到社交用户信息的问题
 * @author TiHom
 */

@Component
public class AppSignUpUtils {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /**
     * 操作数据库的类
     */
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    /**
     * 定位ConnectionFactory的,根据定位拿出connection
     */
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     * 缓存社交网站用户信息到redis
     * @param request
     * @param connectionData
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData){
        //设置10分钟自动清除数据
        redisTemplate.opsForValue().set(getKey(request),connectionData,10,TimeUnit.MINUTES);
    }

    /**
     * 将缓存的社交网站用户信息与系统注册用户信息绑定
     * @param request
     * @param userId
     */
    public void doPostSignUp(WebRequest request,String userId){
        //把之前放在session中的数据拿出来
        String key = getKey(request);
        if(!redisTemplate.hasKey(key)){
            throw new AppSecretException("无法找到缓存的第三方用户信息");
        }
        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
        //根据连接数据中的providerId拿到连接工厂去创建连接
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
                .createConnection(connectionData);
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

        //如果流程完成,在redis中把这个key删掉
        redisTemplate.delete(key);
    }

    /**
     * 获取redis key
     * @param request
     * @return
     */
    private String getKey(WebRequest request) {
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new AppSecretException("设备id参数 不能为空");
        }
        return "tihom:security:social.connect."+deviceId;
    }
}
