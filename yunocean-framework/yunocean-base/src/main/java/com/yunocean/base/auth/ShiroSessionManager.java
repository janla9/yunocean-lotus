/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  SessionManager.java   
 * @Package com.yunocean.base.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月26日 下午4:38:22   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.auth;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import com.yunocean.base.common.SysConstants;
import com.yunocean.base.model.EUser;

import lombok.extern.slf4j.Slf4j;


/** 
 * 自定义session管理 
 * 传统结构项目中，shiro从cookie中读取sessionId以此来维持会话，在前后端分离的项目中（也可在移动APP项目使用），
 * 我们选择在ajax的请求头中传递sessionId，因此需要重写shiro获取sessionId的方式。
 * 自定义MySessionManager类继承DefaultWebSessionManager类，重写getSessionId方法
 * @ClassName:  SessionManager   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月26日 下午4:38:22   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Slf4j
public class ShiroSessionManager extends DefaultWebSessionManager 
{
	public static ShiroSessionManager instance;
	
	public static ShiroSessionManager getInstance() {
		if(instance == null) {
			instance = new ShiroSessionManager();
		}
		return instance;
	}
	
	@Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(SysConstants.AUTHORIZATION);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, SysConstants.REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }	
	
	/**
	 * 获取当前的用户
	 * @Title: getCurrentSessionUser   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: EUser      
	 * @throws
	 */
	public EUser getCurrentSessionUser() {
		EUser user = null;
		try {
			user = SecurityUtils.getSubject().getPrincipal() != null ? (EUser) SecurityUtils.getSubject().getPrincipal() : null;
		} catch(Exception ex) {
			//log.error(ex.getMessage(), ex);
			user = null;
		}	
		return user;
	}
}
