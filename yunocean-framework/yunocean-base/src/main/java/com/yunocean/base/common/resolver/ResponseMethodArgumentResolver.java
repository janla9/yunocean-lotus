/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ResponseMethodArgumentResolver.java   
 * @Package com.yunocean.base.common   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月31日 下午4:25:07   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.yunocean.base.common.annotation.Response;
import com.yunocean.base.common.dto.ResponseDto;

import java.util.Locale;

/**   
 * 参考：https://www.licoy.cn/3238.html
 * 
 * @ClassName:  ResponseMethodArgumentResolver   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月31日 下午4:25:07   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
public class ResponseMethodArgumentResolver implements HandlerMethodArgumentResolver
{
	
    private MessageSource messageSource;
    
    private NativeLocaleResolver localeResolver;
    
    public ResponseMethodArgumentResolver(MessageSource messageSource, NativeLocaleResolver localeResolver) {
    	this.messageSource = messageSource;
    	this.localeResolver = localeResolver;
    }
	
	/**   
	 * <p>Title: supportsParameter</p>   
	 * <p>Description: </p>   
	 * @param parameter
	 * @return   
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)   
	 */ 
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.getParameterType().isAssignableFrom(ResponseDto.class) 
				&& parameter.hasParameterAnnotation(Response.class)) {
			return true;
		}
	    return false;
	}

	/**   
	 * <p>Title: resolveArgument</p>   
	 * <p>Description: </p>   
	 * @param parameter
	 * @param mavContainer
	 * @param webRequest
	 * @param binderFactory
	 * @return
	 * @throws Exception   
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)   
	 */ 
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Locale locale = localeResolver.resolveLocale(request);
		ResponseDto response = new ResponseDto(messageSource, locale);
	    return response;
	}

}
