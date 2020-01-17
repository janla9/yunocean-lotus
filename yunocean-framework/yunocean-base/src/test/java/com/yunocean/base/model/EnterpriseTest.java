/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  EnterpriseTest.java   
 * @Package com.yunocean.base.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月6日 下午4:40:18   
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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunocean.base.dao.EnterpriseMapper;
import com.yunocean.base.service.EBaseService;

/**   
 * @ClassName:  EnterpriseTest   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月6日 下午4:40:18   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EnterpriseTest 
{
	@Resource
    private EBaseService<EnterpriseMapper, Enterprise> enterpriseService;
	
	@Test
	public void getAll() {
		System.out.println("--------------------------------");
		IPage<Enterprise> page = enterpriseService.queryPage(1, 20);
		List<Enterprise> alist = page.getRecords();
		for(Enterprise enter : alist) {
			System.out.println(enter.getId() + "," + enter.getCreateTime());
		}
		System.out.println(alist);
		
		System.out.println("--------------------------------");
	}
}
