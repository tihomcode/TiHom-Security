package com.tihom.security.app;

import com.tihom.secutity.core.social.support.TihomSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 实现这个接口的bena的作用就是Spring容器在初始化之前和初始化之后都要经过下面两个方法
 * @author TiHom
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 在tihomSocialSecurityConfig初始化好之后将signupUrl改掉
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //找到这个bean
        if(StringUtils.equals(beanName,"tihomSocialSecurityConfig")){
            TihomSpringSocialConfigurer configurer = (TihomSpringSocialConfigurer) bean;
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
