/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ShiroRealm.java   
 * @Package com.yunocean.base.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月26日 下午5:21:18   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.yunocean.base.model.EUser;
import com.yunocean.base.service.EUserService;

import lombok.extern.slf4j.Slf4j;


/**   
 * @ClassName:  ShiroRealm   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 "aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18""aliyunv2_18"
 * @date:   2019年12月26日 下午5:21:18   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm
{
	private static ShiroRealm instance;
	
	@Autowired
	@Lazy
	private EUserService eUserService;
	
	
	public static ShiroRealm getInstance() {
		if(instance == null) {
			instance = new ShiroRealm();
			instance.setCredentialsMatcher(ShiroCredentialsMatcher.getInstance());
		}
		return instance;
	}
	
	
	/**
	 * 授权
	 * <p>Title: doGetAuthorizationInfo</p>   
	 * <p>Description: </p>   
	 * @param principals
	 * @return 权限信息，包括角色以及权限  
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("<<开始执行授权操作.......>>");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
		EUser user = (EUser)principals.getPrimaryPrincipal();
		log.info("username:" + user.getUsername());
        
        
        
       
        // 查询用户角色，一个用户可能有多个角色
//        List<SysRole> roles = iRoleService.getUserRoles(user.getUserId());
//
//        for (Role role : roles) {
//            authorizationInfo.addRole(role.getRole());
//            // 根据角色查询权限
//            List<Permission> permissions = iPermissionService.getRolePermissions(role.getRoleId());
//            for (Permission p : permissions) {
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
        return authorizationInfo;
	}
	
	
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("<<开始执行认证操作.....>>");
		//获取用户的输入的账号.
		UsernamePasswordToken accountToken = (UsernamePasswordToken) token;
		// 用户输入用户名
		String inputUsername = accountToken.getUsername();
		EUser user = eUserService.getByUsername(inputUsername);
		if (user != null) {
			return new SimpleAuthenticationInfo(
	                // 这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
	                user,
	                // 密码
	                user.getPassword(),
	                // realm name
	                getName());
        }
		throw new UnknownAccountException();		
	}
}
