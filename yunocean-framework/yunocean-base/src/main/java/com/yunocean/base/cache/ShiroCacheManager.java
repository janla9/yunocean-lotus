/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ShiroCache.java   
 * @Package com.yunocean.base.cache   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月27日 下午4:12:20   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**   
 * shiro cache数据存放redis
 * @ClassName:  ShiroCache   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月27日 下午4:12:20   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */

@Component
public class ShiroCacheManager implements CacheManager 
{
	private final Logger logger = LoggerFactory.getLogger(ShiroCacheManager.class);
	
	@SuppressWarnings("rawtypes")
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	@Autowired		
	protected RedisHelper redisHelper;
	
	private static ShiroCacheManager instance;
	
	public static ShiroCacheManager getInstance() {
		if(instance == null) {
			instance = new ShiroCacheManager();
		}
		return instance;
	}
	
	
	/**   
	 * <p>Title: getCache</p>   
	 * <p>Description: </p>   
	 * @param <K>
	 * @param <V>
	 * @param arg0
	 * @return
	 * @throws CacheException   
	 * @see org.apache.shiro.cache.CacheManager#getCache(java.lang.String)   
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("<get shiro cache, name=" + name + ">");		
		Cache<K, V> cache = caches.get(name);		
		redisHelper.get("");
		if (cache == null) {
			cache = new ShiroCache<K, V>(redisHelper);
			caches.put(name, cache);
		}
		return cache;
	}

}
