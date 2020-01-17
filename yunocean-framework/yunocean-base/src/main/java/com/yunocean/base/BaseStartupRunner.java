/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  BaseStartupRunner.java   
 * @Package com.yunocean.base   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月9日 下午12:11:00   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yunocean.base.service.assist.PermissionsAssist;

/**   
 * @ClassName:  BaseStartupRunner   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月9日 下午12:11:00   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Component("BaseStartupRunner")
@Order(value = 2)
public class BaseStartupRunner implements CommandLineRunner
{

	@Autowired
	private PermissionsAssist permissionsAssist;
	

	@Override
	public void run(String... args) throws Exception {
		permissionsAssist.init();
	}

}
