/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  UserRole.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月19日 下午8:21:02   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * @ClassName:  UserRole   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月19日 下午8:21:02   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("E_USER_ROLE")
public class EUserRole extends EBaseModel
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 3113023120055371693L;
	
	private Long userId;
	
	private Long roleId;
}
