package com.der.security.app.config;

import com.der.security.core.authorzation.mobile.SmsCodeAuthenticationSecurityConfig;
import com.der.security.core.properties.SecurityDefaultConstants;
import com.der.security.core.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableResourceServer
public class DerResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AuthenticationSuccessHandler derAuthenticationSuccessHandler;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(SecurityDefaultConstants.DEFAULT_SIGN_IN_PAGE_URL,
                        SecurityDefaultConstants.DEFAULT_UNAUTHENTICATION_URL
                        ).permitAll()
                /*.anyRequest()
                .authenticated()*/
                .and()
                .csrf().disable()
                //.apply(validateCodeSecurityConfig)
                //.and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .formLogin()
                .loginPage(SecurityDefaultConstants.DEFAULT_UNAUTHENTICATION_URL)
                /* 登录页面 */
                .loginProcessingUrl(SecurityDefaultConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                /* 默认登录操作 */
                .failureHandler(authenticationFailureHandler)
                /* 登录失败处理 */
                .successHandler(derAuthenticationSuccessHandler);
    }
}
