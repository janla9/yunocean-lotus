/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  Permissions.java   
 * @Package com.yunocean.base.common.annotation   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月8日 下午5:10:46   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
 * @ClassName:  Permissions   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月8日 下午5:10:46   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionsParent 
{
	String name() default "";
    
	String value() default "";
}
