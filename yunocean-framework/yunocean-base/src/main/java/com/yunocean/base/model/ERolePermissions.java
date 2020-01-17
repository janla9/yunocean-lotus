/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  EUserPermissions.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月8日 下午1:36:31   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * @ClassName:  EUserPermissions   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月8日 下午1:36:31   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("E_ROLE_PERMISSIONS")
public class ERolePermissions extends EBaseModel
{	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -7142857189581208353L;
	
	private Long roleId;
	
	private Long permissionsId;
}
