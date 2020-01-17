/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  RequestStatusEnums.java   
 * @Package com.yunocean.base.common.enums   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月28日 下午3:03:44   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.enums;

import lombok.Getter;
import lombok.Setter;


/** 
 * 操作状态码  
 * @ClassName:  RequestStatusEnums   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月28日 下午3:03:44   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
public enum RequestStatusEnums 
{
	//系统级别得错误码
	
	//操作成功
	SUCCESS(200, "sys.success"),    
    //参数错误
    PARAM_ERROR(501, "parameter.error"),
    //参数为空
    PARAM_NULL(502, "parameter.empty"),    
    //参数已存在
    PARAM_REPEAT(503, "parameter.exists"),    
    //操作失败
    OPERATION_ERROR(504, "operation.error"),    
    //系统错误
    SYSTEM_ERROR(500, "sys.error"),    
    //系统级别得错误码 END
    
    
    
    
    
    
    
    //账户不存在
    ACCOUNT_UNKNOWN(10000, "account.not.exist"),
    //账号被禁用
    ACCOUNT_IS_DISABLED(10001, "account.disabled"),
    //用户名或密码错误
    INCORRECT_CREDENTIALS(10002,"login.usernamepassword.error"),
    //账号未登录
    NOT_LOGIN_IN(10003, "account.not.login"),
    //认证失败
    AUTH_ERROR(10004, "authentication.failed"),
    //验证码错误
    VALIDATECODE_ERROR(10005, "verification.error"),
    //没有操作权限
    PERMISSION_NEVER(10006, "operation.right.never"),    
    //重复数据
    DUPLICATE_DATA(10007, "duplicate_data"),
    
    //其他错误
    OTHER(-100, "other.error");
	
	
	
    @Getter
    @Setter
    private int code;
    
    @Getter
    @Setter
    private String message;
    

    RequestStatusEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }   
}
