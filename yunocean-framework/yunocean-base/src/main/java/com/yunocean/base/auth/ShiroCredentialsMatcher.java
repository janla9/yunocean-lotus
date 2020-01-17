/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ShiroCredentialsMatcher.java   
 * @Package com.yunocean.base.config.auth   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月27日 下午12:57:09   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
package com.yunocean.base.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunocean.base.utils.SHA256Util;

/**
 * @ClassName: ShiroCredentialsMatcher
 * @Description: 凭证匹配实现类
 * @author: 云海洋智能
 * @date: 2019年12月27日 下午12:57:09
 * 
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved.
 */
public class ShiroCredentialsMatcher extends HashedCredentialsMatcher 
{
	private static final Logger logger = LoggerFactory.getLogger(ShiroCredentialsMatcher.class);
	
	private static ShiroCredentialsMatcher instance;

	/**
	 * 获取匹配器
	 * @Title: getInstance   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: ShiroCredentialsMatcher      
	 * @throws
	 */
	public static ShiroCredentialsMatcher getInstance() {
		if(instance == null) {
			//自定义凭证比较器
			instance = new ShiroCredentialsMatcher();
	        // 加密算法的名称
			instance.setHashAlgorithmName(SHA256Util.ALGORITH_NAME);
	        // 配置加密的次数
			instance.setHashIterations(SHA256Util.HASH_ITERATIONS);
			//true加密用的hex编码，false用的base64编码
			instance.setStoredCredentialsHexEncoded(true);
		}
		return instance;
	}
	
	
	/**
	 * 重写密码验证器
	 * <p>Title: doCredentialsMatch</p>   
	 * <p>Description: </p>   
	 * @param token
	 * @param info
	 * @return   
	 * @see org.apache.shiro.authc.credential.HashedCredentialsMatcher#doCredentialsMatch(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationInfo)
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		logger.info("<<进入自定义密码比较器...>>");
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		String inputName = upt.getUsername();// 用户输入的用户名
		String inputPwd = new String(upt.getPassword());// 用户输入的密码
		String dbPassword = (String) info.getCredentials();// 数据库查询得到的加密后的密码
		// 对用户输入密码进行加密(加密方式,用户输入密码,盐值（用户名）,加密次数)
		String encryptionPwd = SHA256Util.encrypt(inputPwd, inputName);
		System.out.println(encryptionPwd + "," + dbPassword);
		return equals(encryptionPwd, dbPassword);
	}
}
