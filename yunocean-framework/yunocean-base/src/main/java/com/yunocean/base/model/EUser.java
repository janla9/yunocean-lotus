package com.yunocean.base.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户基础信息
 * @author KK
 * e-mail javaw@126.com.com
 * https://wwww.yunocean.com
 * @version 0.1
 * @since 2019/12/16 9:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("E_USER")
public class EUser extends EBaseModel
{
	private static final long serialVersionUID = -6934490362337223518L;
	
	private Long parentId;
	
	private Long enterpriseId;
		
	//TableField 注解新增属性 condition 预处理 WHERE 实体条件自定义运算规则
	@TableField(condition = SqlCondition.LIKE)
    private String username;
	
	@JSONField(serialize = false)
	private String password;
	
	@TableField(condition = SqlCondition.LIKE)
	private String nickname;

	private String realname;
	
	private String description;
	
	//0 男   1 女  默认 1
	private Integer sex;	
	//年龄
	private Integer age;
	
	//工号
	@TableField(condition = SqlCondition.LIKE)
	private String jobNumber;
	
	//电话号码，多个号码逗号分隔，最多5个
	@TableField(condition = SqlCondition.LIKE)
	private String phone;
	
	//座机号码
	@TableField(condition = SqlCondition.LIKE)
	private String telephone;
	
	//身份证
	@TableField(condition = SqlCondition.LIKE)
	private String identityCard;
	
	//地址
	private String address;
	
	//登录登出状态
	private Integer online;
	
	//QQ
	private String qq;
	
	//级别
	private Integer level;
	
	//email
	private String email;
	
	//备注
	private String remark;
	
	//0 离职  对离职用户，将用户名修改为 姓名+ 离职   , 1 在职 
	private Integer status;
	
	//版本扩展   
	private Integer version;
    
    //逻辑删除标记
    @JSONField(serialize = false)
    private Integer deleted;

    //如果实体类中有一个属性为xxx，但是在数据库中没有这个字段，
    //但是在执行插入操作时给实体类的remark属性赋值了，那么可以通过在实体类的
    //角色列表
    //@TableField(exist=false)
    //@JSONField(serialize = false)
    //private List<ERole> roles;
    
}