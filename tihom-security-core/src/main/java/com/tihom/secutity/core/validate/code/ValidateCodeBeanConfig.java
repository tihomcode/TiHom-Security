package com.tihom.secutity.core.validate.code;

import com.tihom.secutity.core.properties.SecurityProperties;
import com.tihom.secutity.core.validate.code.image.ImageCodeGenerator;
import com.tihom.secutity.core.validate.code.sms.DefaultSmsCodeSender;
import com.tihom.secutity.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TiHom
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    //当不存在imageCodeGenerator这个发生器的Bean的时候才进入配置
    @ConditionalOnMissingBean(name = "imageCodeValidateGenerator")  //不在ImageCodeGenerator上配置@Component的原因是为了在这里配置
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
