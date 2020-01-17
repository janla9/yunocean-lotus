/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  AuthenticationData.java   
 * @Package com.yunocean.base.common.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月2日 下午12:47:15   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**   
 * @ClassName:  AuthenticationData   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月2日 下午12:47:15   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Data
public class AuthenticationData implements Serializable 
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -6102107486930397912L;
	
	//NotNull 用户名或密码不能为空
	@NotNull(message = "{usernamepassword.notnull}")	
	//Size 账号错误，位数要为6-16位
	@Size(min = 3,max = 16, message = "{account.length.error}")	
	@ApiModelProperty(value="用户名（3 ~ 16 个字符）",name="username",example="admin", required = true)
	private String username;
	
	//如果密码太长，隐似的报参数错误
	@Size(max = 32, message = "{parameter.error}")	
	@ApiModelProperty(value="密码（最大32个字符",name="password",example="a123456", required = true)
	private String password;
	
	@ApiModelProperty(value="认证类型：1 系统级账号认证   2 企业级账号认证",name="type",example="1", required = true)
	private Integer type;
	
	//验证码或短信验证码	如果太长，隐似的报参数错误	
	@Size(max = 8, message = "{parameter.error}")	
	@ApiModelProperty(value="验证码（最大 8 个字符）",name="vcode",example="xxxx", required = false)
	private String vcode;
}