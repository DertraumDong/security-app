package com.der.security.core.validate.controller;

import com.der.security.core.properties.SecurityDefaultConstants;
import com.der.security.core.validate.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /*@GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws IOException {
        ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));
        //放入到redis中去
        httpSession.setAttribute(CODE_KEY, imageCode);
        //输出
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        ValidateCode validateCode = smsCodeGenerator.generate(request);
        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        //保存到redis
        redisService.set(SmsKey.SMS_KEY_ID,"" + mobile,validateCode);
        //发送到指定手机上
        smsCodeSender.send(mobile,validateCode.getCode());
    }*/

    @GetMapping(SecurityDefaultConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }
}
