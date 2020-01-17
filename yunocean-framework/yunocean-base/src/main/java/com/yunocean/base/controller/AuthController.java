/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  AuthController.java   
 * @Package com.yunocean.base.controller   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月28日 下午2:40:49   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.controller;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunocean.base.common.annotation.Response;
import com.yunocean.base.common.dto.AuthenticationData;
import com.yunocean.base.common.dto.ResponseDto;
import com.yunocean.base.common.enums.RequestStatusEnums;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**   
 * 认证、验证码、微信认证、短信认证
 * @ClassName:  AuthController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月28日 下午2:40:49   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */

@Slf4j
@Api(tags ="认证鉴权")
@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthController 
{
	
	/**
	 * 登录系统
	 * @Title: login   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param authData
	 * @param: @param response
	 * @param: @return      
	 * @return: ResponseDto      
	 * @throws
	 */
	@ApiOperation(value="登录", notes="登录认证", produces="application/json")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "admin", required = true),
//        @ApiImplicitParam(name = "password", value = "密码", defaultValue = "admin", required = true)
//	})
	@PostMapping("/login")
    public ResponseDto login(@RequestBody @ApiParam(name="认证对象",value="传入json格式",required=true) @Valid AuthenticationData authData,
    		@Response ResponseDto response) {
		String username = authData.getUsername();
		String password = authData.getPassword();
		Integer type = authData.getType();
		log.info("username:" + username + ",password:" + password + ",vcode:" + authData.getVcode() + ",type:" + type);
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			
		// 4、认证
        try {
            // 传到 ShiroRealm 类中的方法进行认证
            currentUser.login(token);
            Subject subject = SecurityUtils.getSubject();
            return response.success((String)subject.getSession().getId(), subject.getPrincipal());            
        } catch (UnknownAccountException e) {
        	log.error("<<Exception UnknownAccountException>>", e);
        	return response.failure(RequestStatusEnums.ACCOUNT_UNKNOWN);
        } catch (IncorrectCredentialsException e) {
        	log.error("<<Exception IncorrectCredentialsException>>", e);
            return response.failure(RequestStatusEnums.INCORRECT_CREDENTIALS);
        } catch (AuthenticationException e) {
        	log.error("<<Exception AuthenticationException>>", e);
            return response.failure(RequestStatusEnums.AUTH_ERROR);
        }
	}
	
	/**
	 * 登出操作
	 * @Title: logout   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param response
	 * @param: @return      
	 * @return: ResponseDto      
	 * @throws
	 */
	@ApiOperation(value="登出", notes="退出系统", produces="application/json")
	@GetMapping("/logout")	
    public ResponseDto logout(@Response ResponseDto response) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return response.success();
	}
	
		
	/**
	 * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
	@ApiOperation(hidden = true, value = "")
    @RequestMapping(value = "/unauth")    
    public ResponseDto unauth(@Response ResponseDto response) { 
        return response.failure(RequestStatusEnums.NOT_LOGIN_IN);
    }
    
    /**
     * 跨权访问
     * @Title: unauthorized   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param response
     * @param: @return      
     * @return: ResponseDto      
     * @throws
     */
	@ApiOperation(hidden = true, value = "")
    @RequestMapping("/unauthorized")
    public ResponseDto unauthorized(@Response ResponseDto response) {
        return response.failure(RequestStatusEnums.PERMISSION_NEVER);
    }

}
