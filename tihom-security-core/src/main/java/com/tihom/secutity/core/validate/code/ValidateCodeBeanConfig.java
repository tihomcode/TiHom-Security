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
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置
 * @author TiHom
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 图片验证码图片生成器
     * @return
     */
    @Bean
    //当不存在imageCodeGenerator这个发生器的Bean的时候才进入配置
    @ConditionalOnMissingBean(name = "imageCodeValidateGenerator")  //不在ImageCodeGenerator上配置@Component的原因是为了在这里配置
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * 短信验证码发送器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
