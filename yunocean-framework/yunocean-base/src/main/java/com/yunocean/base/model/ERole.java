package com.yunocean.base.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色
 * @author KK
 * e-mail javaw@126.com.com
 * https://wwww.yunocean.com
 * @version 0.1
 * @since 2019/12/16 9:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("E_ROLE")
public class ERole extends EBaseModel
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 2720079843895281838L;
	
	@TableField(condition = SqlCondition.LIKE)
	@NotNull(message = "{parameter.empty}")
	@Size(min = 2,max = 64, message = "{parameter.error}")	
	@ApiModelProperty(value="角色名（2 ~ 64 个字符）", name="name", example="管理组", required = true)
    private String name;
	
	@Size(max = 255, message = "{parameter.error}")	
	@ApiModelProperty(value="描述（最大 255 个字符）",name="description",example="角色分组描述")
	private String description;
	
	//权限列表
	@TableField(exist=false)
    @JSONField(serialize = false)
	private List<Permissions> permissions;
}
