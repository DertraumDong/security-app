package com.der.security.core.validate.code;

import com.der.security.core.properties.SecurityDefaultProperties;
import com.der.security.core.validate.code.image.DefaultImageCodeGenerator;
import com.der.security.core.validate.code.sms.DefaultSmsCodeGenerator;
import com.der.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.der.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeConfig {

    @Autowired
    private SecurityDefaultProperties securityDefaultProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        DefaultImageCodeGenerator imageCodeGenerator = new DefaultImageCodeGenerator();
        imageCodeGenerator.setSecurityDefaultProperties(securityDefaultProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    public ValidateCodeGenerator smsValidateCodeGenerator(){
        DefaultSmsCodeGenerator smsCodeGenerator = new DefaultSmsCodeGenerator();
        smsCodeGenerator.setSecurityDefaultProperties(securityDefaultProperties);
        return smsCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        SmsCodeSender smsCodeSender = new DefaultSmsCodeSender();
        return smsCodeSender;
    }
}
