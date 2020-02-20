package com.der.security.core.validate.code.sms;

import com.der.security.core.properties.SecurityDefaultProperties;
import com.der.security.core.validate.code.ValidateCode;
import com.der.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidateCodeGenerator")
public class DefaultSmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityDefaultProperties securityDefaultProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityDefaultProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityDefaultProperties.getCode().getSms().getExpireIn());
    }

    public SecurityDefaultProperties getSecurityDefaultProperties() {
        return securityDefaultProperties;
    }

    public void setSecurityDefaultProperties(SecurityDefaultProperties securityDefaultProperties) {
        this.securityDefaultProperties = securityDefaultProperties;
    }

}
