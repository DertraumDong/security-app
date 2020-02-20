package com.der.security.core.validate.code.image;

import com.der.security.core.validate.ValidateCodeRepository;
import com.der.security.core.validate.ValidateCodeType;
import com.der.security.core.validate.code.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;

@Component
public class ImageValidateCodeRepository implements ValidateCodeRepository {

    public static final String CODE_KEY = "IMAGE_KEY";

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType codeType) {
        HttpSession httpSession = request.getRequest().getSession();
        httpSession.setAttribute(getSessionKey(codeType),code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
        HttpSession httpSession = request.getRequest().getSession();
        return (ValidateCode) httpSession.getAttribute(getSessionKey(codeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        HttpSession httpSession = request.getRequest().getSession();
        httpSession.removeAttribute(getSessionKey(codeType));
    }

    /**
     * 构建验证码放入session时的key
     *
     * @return
     */
    private String getSessionKey(ValidateCodeType validateCodeType) {
        return CODE_KEY + validateCodeType.toString().toUpperCase();
    }
}
