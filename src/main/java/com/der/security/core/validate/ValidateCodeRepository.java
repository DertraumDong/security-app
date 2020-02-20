/**
 * 
 */
package com.der.security.core.validate;

import com.der.security.core.validate.code.ValidateCode;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 * 
 * @author zhailiang
 *
 */
public interface ValidateCodeRepository {

	/**
	 * 保存验证码
	 * @param request
	 * @param code
	 * @param validateCodeType
	 */
	void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) throws ServletRequestBindingException;
	/**
	 * 获取验证码
	 * @param request
	 * @param validateCodeType
	 * @return
	 */
	ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) throws ServletRequestBindingException;
	/**
	 * 移除验证码
	 * @param request
	 * @param codeType
	 */
	void remove(ServletWebRequest request, ValidateCodeType codeType) throws ServletRequestBindingException;

}
