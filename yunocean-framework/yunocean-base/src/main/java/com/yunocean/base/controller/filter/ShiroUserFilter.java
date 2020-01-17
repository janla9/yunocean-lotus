/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ShiroAuthenticationFilter.java   
 * @Package com.yunocean.base.controller.filter   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月2日 下午1:57:15   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.controller.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

/**   
 * 重写shiro的用户过滤拦截器：解决前后端分离跨域问题 1、前端发送OPTIONS问题  2、跨域问题
 * @ClassName:  ShiroAuthenticationFilter   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月2日 下午1:57:15   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
//@Slf4j
public class ShiroUserFilter extends UserFilter 
{
	/**
	 * 在访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true
	 * <p>Title: preHandle</p>   
	 * <p>Description: </p>   
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception   
	 * @see org.apache.shiro.web.filter.PathMatchingFilter#preHandle(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        setHeader(httpRequest,httpResponse);
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
        	//log.debug("<Reuqest OPTIONS>");  
        	httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request,response);
	}
	
	
	/**
	 * 返回给前端前，把头信息修改一下
	 * @Title: setHeader   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param request
	 * @param: @param response      
	 * @return: void      
	 * @throws
	 */
	private void setHeader(HttpServletRequest request,HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");        
    }
}
