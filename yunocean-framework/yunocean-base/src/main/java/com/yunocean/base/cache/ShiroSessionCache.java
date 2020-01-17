/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ShiroSessionCache.java   
 * @Package com.yunocean.base.cache   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月27日 下午2:19:55   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.yunocean.base.common.SysConstants;

/**   
 * @ClassName:  ShiroSessionCache   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月27日 下午2:19:55   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */

@Component
public class ShiroSessionCache extends AbstractSessionDAO 
{
	private static Logger logger = LoggerFactory.getLogger(ShiroSessionCache.class);

	@Autowired
	@Lazy
	protected RedisHelper redisHelper;
	
	
	public ShiroSessionCache() {
		super();
	}
	
	
	/**   
	 * <p>Title: update</p>   
	 * <p>Description: </p>   
	 * @param session
	 * @throws UnknownSessionException   
	 * @see org.apache.shiro.session.mgt.eis.SessionDAO#update(org.apache.shiro.session.Session)   
	 */ 
	@Override
	public void update(Session session) throws UnknownSessionException {
		//如果session已失效，不建议存储了。
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
		this.saveSession(session);		
	}

	/**   
	 * <p>Title: delete</p>   
	 * <p>Description: </p>   
	 * @param session   
	 * @see org.apache.shiro.session.mgt.eis.SessionDAO#delete(org.apache.shiro.session.Session)   
	 */ 
	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			logger.error("<session or session id is null>");
			return;
		}
		redisHelper.remove(getRedisSessionKey(session.getId()));
	}

	/**   
	 * <p>Title: getActiveSessions</p>   
	 * <p>Description: </p>   
	 * @return   
	 * @see org.apache.shiro.session.mgt.eis.SessionDAO#getActiveSessions()   
	 */ 
	@Override
	public Collection<Session> getActiveSessions() {
		Set<String> keys = redisHelper.keys(SysConstants.DEFAULT_SHIRO_SESSION_KEY_PREFIX + "*");
		Set<Session> sessions = new HashSet<Session>();
		if(!CollectionUtils.isEmpty(keys)) {
			for (String key : keys) {
                Session s = (Session) redisHelper.get(key);
                sessions.add(s);
            }
		}
		return sessions;
	}

	/**   
	 * <p>Title: doCreate</p>   
	 * <p>Description: </p>   
	 * @param session
	 * @return   
	 * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doCreate(org.apache.shiro.session.Session)   
	 */ 
	@Override
	protected Serializable doCreate(Session session) {
		if (session == null) {
			logger.error("<session is null>");
			throw new UnknownSessionException("session is null");
		}
		
		Serializable sessionId = this.generateSessionId(session);  
		this.assignSessionId(session, sessionId);
        this.saveSession(session);
		return sessionId;		
	}

	/**   
	 * <p>Title: doReadSession</p>   
	 * <p>Description: </p>   
	 * @param sessionId
	 * @return   
	 * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doReadSession(java.io.Serializable)   
	 */ 
	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			logger.warn("<session id is null>");
			return null;
		}
		Session session = (Session) redisHelper.get(getRedisSessionKey(sessionId));		
		return session;
	}
	
	
	/**
	   *  存储session
	 * @Title: saveSession   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param session
	 * @param: @throws UnknownSessionException      
	 * @return: void      
	 * @throws
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			logger.error("<session or session id is null>");
			throw new UnknownSessionException("session or session id is null");
		}
		String key = getRedisSessionKey(session.getId());
		boolean isSuccess = redisHelper.put(key, session, SysConstants.DEFAULT_SESSION_KEY_TIMEOUT_MILLI);
		if(!isSuccess) {
			throw new UnknownSessionException("redis session save exception");
		}
	}
	
	/**
	   * 存储在redis里的session同意加一个前缀
	 * @Title: getRedisSessionKey   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param sessionId
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	private String getRedisSessionKey(Serializable sessionId) {
		return SysConstants.DEFAULT_SHIRO_SESSION_KEY_PREFIX + sessionId;
	}
}
