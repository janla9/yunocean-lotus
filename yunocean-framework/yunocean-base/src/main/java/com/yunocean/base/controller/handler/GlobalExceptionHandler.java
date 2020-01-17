/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  GlobalExceptionHandler.java   
 * @Package com.yunocean.base.controller.handler   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月30日 下午5:25:13   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.controller.handler;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunocean.base.common.annotation.Response;
import com.yunocean.base.common.dto.ResponseDto;
import com.yunocean.base.common.enums.RequestStatusEnums;
import com.yunocean.base.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**   
 * 控制器层面全局异常处理
 * @ClassName:  GlobalExceptionHandler   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月30日 下午5:25:13   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler 
{
	/**
	 * 处理所有不可知的异常
	 * @Title: handleException   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param e
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	@ExceptionHandler(Exception.class)
    @ResponseBody
	public ResponseDto handleException(Exception e, @Response ResponseDto response) throws Exception {
		log.error(e.getMessage(), e);
		if(e instanceof DuplicateKeyException) {
			return response.failure(RequestStatusEnums.DUPLICATE_DATA);
		} else if(e instanceof UnauthorizedException) {
			return response.failure(RequestStatusEnums.PERMISSION_NEVER);
		}
		//由于Exception是异常的父类，如果你的项目中出现过在自定义异常中使用@ResponseStatus的情况，
		//你的初衷是碰到那个自定义异常响应对应的状态码，而这个控制器增强处理类，会首先进入，并直接
		//返回，不会再有@ResponseStatus的事情了，这里为了解决这种纰漏，我提供了一种解决方式。
		//if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
		//		throw e;
		//}
		return response.failure(RequestStatusEnums.SYSTEM_ERROR);
	}
	
	/**
	 * 处理所有业务异常
	 * @Title: handleBusinessException   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param e
	 * @param: @return      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	@ExceptionHandler(BusinessException.class)
    @ResponseBody
	public ResponseDto handleBusinessException(BusinessException e, @Response ResponseDto response) {
		log.error(e.getMessage(), e);
        return response.failure(RequestStatusEnums.SYSTEM_ERROR);
	}
	
	/**
	 * 处理所有接口数据验证异常
	 * @Title: handleMethodArgumentNotValidException   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param e
	 * @param: @return      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
	public ResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
        	sb.append(objectError.getDefaultMessage());
        	break;
        }
        log.error(sb.toString());
        return new ResponseDto(sb.toString());
	}
	
}
