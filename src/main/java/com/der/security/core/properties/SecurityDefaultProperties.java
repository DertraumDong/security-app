package com.der.security.core.properties;

import com.der.security.core.properties.oauth.OAuth2Properties;
import com.der.security.core.properties.rememberMe.RememberMeProperties;
import com.der.security.core.properties.validateCode.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "der.security",ignoreUnknownFields = true)
@EnableConfigurationProperties(SecurityDefaultProperties.class)
public class SecurityDefaultProperties {

    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 记住我配置
     */
    private RememberMeProperties rememberMe = new RememberMeProperties();

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public RememberMeProperties getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(RememberMeProperties rememberMe) {
        this.rememberMe = rememberMe;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
