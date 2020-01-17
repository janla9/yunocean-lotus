/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  RedisTest.java   
 * @Package com.yunocean.base   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月24日 下午2:27:59   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yunocean.base.cache.RedisHelper;
import com.yunocean.base.common.dto.MessageDto;
import com.yunocean.base.message.RedisMessageAdapter;
import com.yunocean.base.model.EUser;

/**   
 * @ClassName:  RedisTest   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月24日 下午2:27:59   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest 
{
	@Resource
	RedisHelper redisHelper;
	@Autowired
	private RedisMessageAdapter redisMessageAdapter;
	
	@Test
	public void testSet(){
		EUser user = new EUser();
		user.setUsername("admin");
		user.setPassword("123456");
		redisHelper.put("admin", user);
    }
	
	@Test
	public void testGet() {
		EUser user = (EUser)redisHelper.get("admin");
		System.out.println(user.getUsername() + "," + user.getPassword());
	}
	
	@Test
	public void testIncrement() {
		System.out.println(redisHelper.increment("autoId", 1));
		System.out.println(redisHelper.increment("autoId", 1));
		
		System.out.println(redisHelper.decrement("autoId", 1));
		System.out.println(redisHelper.decrement("autoId", 1));
	}
	
	@Test
	public void testSubscribe() {
		MessageDto messageDto = new MessageDto();
		messageDto.setName("janla");
		redisMessageAdapter.sendMessage(messageDto);
	}
	
	@Test
	public void testSetHash() {
		EUser user = new EUser();
		user.setId(1l);
		user.setUsername("janla");
		redisHelper.hSet(EUser.class.getName(), "janla", user);
	}
	
	@Test
	public void testGetHash() {
		EUser user = (EUser)redisHelper.hGet(EUser.class.getName(), "janla22");
		System.out.println("--->" + user);
	}
	
}
