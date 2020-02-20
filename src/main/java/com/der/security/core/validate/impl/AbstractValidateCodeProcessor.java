/**
 * 
 */
package com.der.security.core.validate.impl;


import com.der.security.core.validate.ValidateCodeException;
import com.der.security.core.validate.ValidateCodeProcessor;
import com.der.security.core.validate.ValidateCodeRepository;
import com.der.security.core.validate.ValidateCodeType;
import com.der.security.core.validate.code.ValidateCode;
import com.der.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 抽象的图片验证码处理器
 * @author zhailiang
 *
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	
	@Autowired
	private Map<String, ValidateCodeRepository> validateCodeRepositorys;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) throws ServletRequestBindingException {
		String type = getValidateCodeType(request).toString().toLowerCase();
		String repositoryName = type + ValidateCodeRepository.class.getSimpleName();
		ValidateCodeRepository validateCodeRepository = validateCodeRepositorys.get(repositoryName);
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getValidateCodeType(request));
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "ValidateCodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	@Override
	public void validate(ServletWebRequest request) throws ServletRequestBindingException {
		ValidateCodeType codeType = getValidateCodeType(request);
		String type = codeType.toString().toLowerCase();
		String typeName = type + ValidateCodeRepository.class.getSimpleName();;
		ValidateCodeRepository validateCodeRepository = validateCodeRepositorys.get(typeName);
		C codeInSession = (C) validateCodeRepository.get(request,codeType);

		String codeInRequest;
		try {
			//获取request中的参数值
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(codeType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			if(type.toUpperCase().equals(ValidateCodeType.SMS.toString())){
				throw new ValidateCodeException(codeType + "验证码已过期");
			}
			throw new ValidateCodeException(codeType + "验证码不存在");
		}

		if(type.toUpperCase().equals(ValidateCodeType.IMAGE)){
			if (codeInSession.isExpried()) {
				validateCodeRepository.remove(request,codeType);
				throw new ValidateCodeException(codeType + "验证码已过期");
			}
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(codeType + "验证码不匹配");
		}
		validateCodeRepository.remove(request,codeType);
	}
}
