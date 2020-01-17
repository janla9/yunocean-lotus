/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  Message.java   
 * @Package com.yunocean.base.message.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月25日 下午12:28:39   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 消息对象
 * @ClassName:  Message   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月25日 下午12:28:39   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Data
public class MessageDto implements Serializable
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -7305970038566110843L;

	/**消息类型*/
	private Integer type;
	
	/**消息ID*/
	private Long id;
	
	/**消息名称*/
	private String name;
	
	private LocalDateTime createTime;
	
	/**关联的对象*/
	private Object mObj;
}
