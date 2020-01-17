/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  BusinessException.java   
 * @Package com.yunocean.base.common.exception   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月30日 下午5:33:39   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.exception;

/**   
 * 业务异常类
 * @ClassName:  BusinessException   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月30日 下午5:33:39   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
public class BusinessException extends RuntimeException {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -1095589251812945346L;
	
	public BusinessException(String message){
        super(message);
    }
}
