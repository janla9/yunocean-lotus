/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  SysRoleTest.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月4日 下午3:02:05   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.model;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yunocean.base.service.ERoleService;

/**   
 * @ClassName:  SysRoleTest   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月4日 下午3:02:05   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleTest 
{
	@Resource
    private ERoleService userRoleService;
	
	@Test
	public void getAll() {
		System.out.println("--------------------------------");
		List<ERole> roleList = userRoleService.listAll();
		for(ERole sysRole : roleList) {
			System.out.println(sysRole.getId() + "," + sysRole.getCreateTime());
		}
		System.out.println(roleList);
		System.out.println("--------------------------------");
	}
}
