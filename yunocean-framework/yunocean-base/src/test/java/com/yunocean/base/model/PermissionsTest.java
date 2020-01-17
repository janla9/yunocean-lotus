/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  PermissionsTest.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月9日 下午1:37:12   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.model;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yunocean.base.service.PermissionsService;

/**   
 * @ClassName:  PermissionsTest   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月9日 下午1:37:12   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionsTest 
{
	@Resource
    private PermissionsService permissionsService;
	
	@Test
	public void getMaxId() {
		Long maxId = permissionsService.getMaxId();
		System.out.println("--->" + maxId);
	}
}
