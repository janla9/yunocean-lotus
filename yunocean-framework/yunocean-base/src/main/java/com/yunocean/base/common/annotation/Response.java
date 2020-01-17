/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  Response.java   
 * @Package com.yunocean.base.common.annotation   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月31日 下午4:22:42   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 * @ClassName:  Response   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月31日 下午4:22:42   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Response 
{
	String value() default "uid";
}
