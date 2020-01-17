/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  EPermissions.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月8日 上午11:58:30   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * 权限对象,权限属于整系统，固定下来的常量不可动态新增
 * @ClassName:  EPermissions   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月8日 上午11:58:30   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("PERMISSIONS")
public class Permissions extends BaseModel
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -7437482550576800932L;
	
	@TableField(condition = SqlCondition.LIKE)
	@NotNull(message = "{parameter.empty}")
	@Size(min = 2,max = 64, message = "{parameter.error}")	
	@ApiModelProperty(value="权限名（2 ~ 64 个字符）", name="name", example="用户-添加", required = true)
	private String name;
	
	@Size(max = 255, message = "{parameter.error}")	
	@ApiModelProperty(value="描述（最大 255 个字符）",name="description",example="无")
	private String description;
	
	private Long parentId;
	
	private String parentIds;
	
	//权限：user:list  或  user:add 或  user:delete
	private String permission;
	
	//资源的类型 0 根菜单   1 一级菜单   2 2级菜单       100 按钮
	private Integer resourceType = 0;
	
	//是否不显示   1 显示  0 隐藏
	private Boolean display = true;
	
	//排序，根据RESOURCE_TYPE按类别排序
	private Integer sort = 0;
	
	//菜单的图标
	private String icon;
	
	//外链菜单打开方式 0/内部打开 1/外部打开
	private Boolean internalOrExternal = false;
	
	//URL  如：/user/add
	private String url;
}
