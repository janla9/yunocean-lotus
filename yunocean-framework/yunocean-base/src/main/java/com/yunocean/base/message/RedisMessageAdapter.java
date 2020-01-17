/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  RedisService.java   
 * @Package com.yunocean.base.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月25日 上午11:49:07   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yunocean.base.cache.RedisHelper;
import com.yunocean.base.common.dto.MessageDto;

/**   
 * redis操作
 * @ClassName:  RedisService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月25日 上午11:49:07   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */

@Service
public class RedisMessageAdapter 
{
	private static final Logger logger = LoggerFactory.getLogger(RedisMessageAdapter.class);

	private String channel = "Message-Channel-Yunocean";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private RedisHelper redisHelper;
	
	/**
	  * 消息送达
	 * @Title: receiveMessage   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param message      
	 * @return: void      
	 * @throws
	 */
	public void receiveMessage(String message) {
        MessageDto messageDto = (MessageDto)redisTemplate.getValueSerializer().deserialize(message.getBytes());
        logger.info("<<Message receive>>" + messageDto.getName());
	}
	
	/**
	   *  发送消息
	 * @Title: sendMessage   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param messageDto      
	 * @return: void      
	 * @throws
	 */
	public void sendMessage(MessageDto messageDto) {
		redisHelper.convertAndSend(channel, messageDto);
	}

	/**
	 * 获取消息频道描述
	 * @Title: getChannel   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String getChannel() {
		return channel;
	}
}
