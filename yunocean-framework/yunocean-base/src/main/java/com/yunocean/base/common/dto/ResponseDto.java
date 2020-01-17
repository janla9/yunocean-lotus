/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ResponseDto.java   
 * @Package com.yunocean.base.common.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月28日 下午3:02:18   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.dto;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunocean.base.common.enums.RequestStatusEnums;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**   
 * @ClassName:  ResponseDto   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月28日 下午3:02:18   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Data
@AllArgsConstructor
public class ResponseDto implements Serializable 
{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -7845752215244123691L;

	@JSONField(serialize = false)
	@ApiModelProperty(hidden = true)
	private MessageSource messageSource;
	@JSONField(serialize = false)
	@ApiModelProperty(hidden = true)
	private Locale locale;
		
	@ApiModelProperty(value="状态码", name="code", example="200")
	private Integer code;
	
	@ApiModelProperty(value="状态描述", name="message", example="操作成功")
    private String message;
	
	@ApiModelProperty(value="查询返回的数据结构体（JSON格式）", name="name", example="JSON格式")
    private Object data;
    
    public ResponseDto() {}
    
    public ResponseDto(String errorMessage) {
    	this.code = RequestStatusEnums.PARAM_ERROR.getCode();
    	this.message = errorMessage;
    }
    
    public ResponseDto(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    /**
     * 返回成功信息
     * @param data      信息内容
     * @param <T>
     * @return
     */
    public ResponseDto success(Object data) {
    	this.code = RequestStatusEnums.SUCCESS.getCode();
    	this.message = messageSource.getMessage(RequestStatusEnums.SUCCESS.getMessage(), null, locale);
    	this.data = data; 
    	return this;
    }
    
    public ResponseDto success(String message, Object data) {
    	this.code = RequestStatusEnums.SUCCESS.getCode();
    	this.message = message;
    	this.data = data; 
    	return this;
    }

    /**
     * 返回成功信息
     * @return
     */
    public ResponseDto success() {
    	this.code = RequestStatusEnums.SUCCESS.getCode();
    	this.message = messageSource.getMessage(RequestStatusEnums.SUCCESS.getMessage(), null, locale);
        return this;
    }
    
    /**
     * 返回操作失败的原因
     * @Title: failure   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param statusEnums
     * @param: @return      
     * @return: ResponseDto      
     * @throws
     */
    public ResponseDto failure(RequestStatusEnums statusEnums) {
    	this.code = statusEnums.getCode();
    	this.message = messageSource.getMessage(statusEnums.getMessage(), null, locale);
    	return this;
    }
    
    /**
     * 操作错误
     * @Title: operFailure   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @return      
     * @return: ResponseDto      
     * @throws
     */
    public ResponseDto operFailure() {
    	this.code = RequestStatusEnums.OPERATION_ERROR.getCode();
    	this.message = messageSource.getMessage(RequestStatusEnums.OPERATION_ERROR.getMessage(), null, locale);
        return this;
    }
    
    /**
     * 参数错误条用
     * @Title: failure   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param errorMessage
     * @param: @return      
     * @return: ResponseDto      
     * @throws
     */
    public ResponseDto failureParam(String errorMessage) { 
    	this.code = RequestStatusEnums.OTHER.getCode();
    	this.message = messageSource.getMessage(errorMessage, null, locale);
    	return this;
    }
}
