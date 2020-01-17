/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  BaseModel.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月6日 上午11:35:49   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.model;

import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * 企业对象基础数据类
 * @ClassName:  BaseModel   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @param <T>
 * @date:   2020年1月6日 上午11:35:49   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class EBaseModel extends Model<EBaseModel>
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -9112671234172388582L;
	
	public static final String FIELD_ENTERPRISE_ID = "enterpriseId";
	
	public static final String FIELD_UPDATE_TIME = "updateTime";
	
	public static final String FIELD_CREATE_ID = "createId";
	
	public static final String FIELD_UPDATE_ID = "updateId";

	
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value="修改，删除操作必填", name="id")
    protected Long id;
	
	/**
	 * @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	 * @JsonSerialize(using = LocalDateTimeSerializer.class)
	 * 解决redis序列号LocalDateTime得问题
	 */
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@TableField(value="CREATE_TIME", fill = FieldFill.INSERT, update="NOW()")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(hidden = true)
	protected LocalDateTime createTime;	
	
	//例如：@TableField(.. , update="now()") 使用数据库时间
	//输出 SQL 为：update 表 set 字段=now() where ...
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@TableField(value="UPDATE_TIME", fill = FieldFill.INSERT_UPDATE, update="NOW()")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(hidden = true)
	protected LocalDateTime updateTime;
	
	//企业ID ENTERPRISE_ID
	@ApiModelProperty(hidden = true)
	protected Long enterpriseId;
	
	@ApiModelProperty(hidden = true)
	@JSONField(serialize = false)
	protected Long createId;
	
	@ApiModelProperty(hidden = true)
	@JSONField(serialize = false)
	protected Long updateId;
	
}
