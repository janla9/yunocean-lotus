/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ShiroSessionListener.java   
 * @Package com.yunocean.base.auth   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月27日 下午3:21:12   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.auth;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**   
  *  登录登出状态监听器
 * @ClassName:  ShiroSessionListener   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月27日 下午3:21:12   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Slf4j
@Component
public class ShiroSessionListener implements SessionListener
{
	/**
	 * SESSION 统计，本地缓存，分布式模式下需要存储到redis
	 */
	private final AtomicInteger sessionCount = new AtomicInteger(0);

	/**   
	 * <p>Title: onStart</p>   
	 * <p>Description: </p>   
	 * @param session   
	 * @see org.apache.shiro.session.SessionListener#onStart(org.apache.shiro.session.Session)   
	 */ 
	@Override
	public void onStart(Session session) {
		int onlineCount = sessionCount.incrementAndGet();
		log.info("<onStart Online Count: " + onlineCount + " >");
	}

	/**   
	 * <p>Title: onStop</p>   
	 * <p>Description: </p>   
	 * @param session   
	 * @see org.apache.shiro.session.SessionListener#onStop(org.apache.shiro.session.Session)   
	 */ 
	@Override
	public void onStop(Session session) {
		int onlineCount = sessionCount.decrementAndGet();
		log.info("<onStop Online Count: " + onlineCount + " >");
	}

	/**   
	 * <p>Title: onExpiration</p>   
	 * <p>Description: </p>   
	 * @param session   
	 * @see org.apache.shiro.session.SessionListener#onExpiration(org.apache.shiro.session.Session)   
	 */ 
	@Override
	public void onExpiration(Session session) {
		int onlineCount = sessionCount.decrementAndGet();
		log.info("<onExpiration Online Count: " + onlineCount + " >");
	}
	
	
	public int getSessionCount() {
        return sessionCount.get();
    }

}
