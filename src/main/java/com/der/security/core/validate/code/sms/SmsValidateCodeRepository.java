package com.der.security.core.validate.code.sms;

import com.alibaba.fastjson.JSON;
import com.der.security.core.validate.ValidateCodeRepository;
import com.der.security.core.validate.ValidateCodeType;
import com.der.security.core.validate.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SmsValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"mobile");
        redisTemplate.opsForValue().set("security_sms_code"+ mobile, JSON.toJSONString(code));
        /*boolean success = redisService.set(SmsKey.SMS_KEY_ID,""+mobile, code);
        if(!success){
            throw new ValidateCodeException("发送验证码失败");
        }*/
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"mobile");
        ValidateCode validateCode = JSON.toJavaObject(JSON.parseObject((String) redisTemplate.opsForValue().get("security_sms_code"+ mobile)), ValidateCode.class);
        //ValidateCode validateCode = redisService.get(SmsKey.SMS_KEY_ID,mobile,ValidateCode.class);
        return validateCode;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"mobile");
        redisTemplate.delete("security_sms_code"+ mobile);
        //redisService.delete(SmsKey.SMS_KEY_ID,mobile);
    }
}
