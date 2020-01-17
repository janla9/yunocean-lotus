/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  RedisCache.java   
 * @Package com.yunocean.base.cache   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月27日 下午4:20:28   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.crazycake.shiro.exception.CacheManagerPrincipalIdNotAssignedException;
import org.crazycake.shiro.exception.PrincipalIdNullException;
import org.crazycake.shiro.exception.PrincipalInstanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yunocean.base.common.SysConstants;

/**   
 * @ClassName:  RedisCache   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月27日 下午4:20:28   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */

@Component
public class ShiroCache<K, V>  implements Cache<K, V> 
{
	private static Logger logger = LoggerFactory.getLogger(ShiroCache.class);
	
	protected RedisHelper redisHelper;
	
	public ShiroCache(RedisHelper redisHelper) {
		this.redisHelper = redisHelper;
	}
	
	/**   
	 * <p>Title: clear</p>   
	 * <p>Description: </p>   
	 * @throws CacheException   
	 * @see org.apache.shiro.cache.Cache#clear()   
	 */ 
	@Override
	public void clear() throws CacheException {
		Set<String> keys = redisHelper.keys(SysConstants.DEFAULT_SHIRO_CACHE_KEY_PREFIX + "*");
		if (CollectionUtils.isEmpty(keys)) {
			return;
		}
		for(String key : keys) {
			redisHelper.remove(key);
		}
	}

	/**   
	 * <p>Title: get</p>   
	 * <p>Description: </p>   
	 * @param arg0
	 * @return
	 * @throws CacheException   
	 * @see org.apache.shiro.cache.Cache#get(java.lang.Object)   
	 */ 
	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		logger.debug("<get key [" + key + "]>");
		if (key == null) {
			return null;
		}
		
		String cacheKey = getShiroCacheKey(key);		
		V value = (V)redisHelper.get(cacheKey);
		return value;
	}

	/**   
	 * <p>Title: keys</p>   
	 * <p>Description: </p>   
	 * @return   
	 * @see org.apache.shiro.cache.Cache#keys()   
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		Set<String> keys = redisHelper.keys(SysConstants.DEFAULT_SHIRO_CACHE_KEY_PREFIX + "*");
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}
		return (Set<K>) keys;
	}

	/**   
	 * <p>Title: put</p>   
	 * <p>Description: </p>   
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws CacheException   
	 * @see org.apache.shiro.cache.Cache#put(java.lang.Object, java.lang.Object)   
	 */ 
	@Override
	public V put(K key, V value) throws CacheException {
		if (key == null) {
			logger.warn("<Saving a null key is meaningless, return value directly without call Redis.>");
			return value;
		}
		String cacheKey = getShiroCacheKey(key);
		redisHelper.put(cacheKey, value);
		return value;
	}

	/**   
	 * <p>Title: remove</p>   
	 * <p>Description: </p>   
	 * @param arg0
	 * @return
	 * @throws CacheException   
	 * @see org.apache.shiro.cache.Cache#remove(java.lang.Object)   
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public V remove(K key) throws CacheException {
		if (key == null) {
            return null;
        }
		logger.debug("<remove key [" + key + "]>");
		String cacheKey = getShiroCacheKey(key);
		Object obj = redisHelper.get(cacheKey);
		if(obj == null) return null;		
		V value = (V)obj;
		redisHelper.remove(cacheKey);
		return value;
	}

	/**   
	 * <p>Title: size</p>   
	 * <p>Description: </p>   
	 * @return   
	 * @see org.apache.shiro.cache.Cache#size()   
	 */ 
	@Override
	public int size() {
		Long longSize = 0L;
		longSize = redisHelper.size(SysConstants.DEFAULT_SHIRO_CACHE_KEY_PREFIX + "*");
		return longSize.intValue();
	}

	/**   
	 * <p>Title: values</p>   
	 * <p>Description: </p>   
	 * @return   
	 * @see org.apache.shiro.cache.Cache#values()   
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> values() {
		Set<String> keys = redisHelper.keys(SysConstants.DEFAULT_SHIRO_CACHE_KEY_PREFIX + "*");
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}
		List<V> values = new ArrayList<V>(keys.size());
		for(String key : keys) {
			V value = (V)redisHelper.get(key);
			values.add(value);
		}
		return Collections.unmodifiableList(values);
	}
	
	
	
	private String getShiroCacheKey(K key) {
		if (key == null) {
			return null;
		}
		return SysConstants.DEFAULT_SHIRO_CACHE_KEY_PREFIX + getStringShiroKey(key);
	}
	
	private String getStringShiroKey(K key) {
		String shiroKey;
		if (key instanceof PrincipalCollection) {
			shiroKey = getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
        } else {
        	shiroKey = key.toString();
		}
		return shiroKey;
	}
	
	private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
		Object principalObject = key.getPrimaryPrincipal();
		if (principalObject instanceof String) {
		    return principalObject.toString();
		}
		Method pincipalIdGetter = getPrincipalIdGetter(principalObject);
		return getIdObj(principalObject, pincipalIdGetter);
	}
	
	private Method getPrincipalIdGetter(Object principalObject) {
		Method pincipalIdGetter = null;
		String principalIdMethodName = this.getPrincipalIdMethodName();
		try {
			pincipalIdGetter = principalObject.getClass().getMethod(principalIdMethodName);
		} catch (NoSuchMethodException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME);
		}
		return pincipalIdGetter;
	}
	
	private String getIdObj(Object principalObject, Method pincipalIdGetter) {
		String redisKey;
		try {
		    Object idObj = pincipalIdGetter.invoke(principalObject);
		    if (idObj == null) {
		        throw new PrincipalIdNullException(principalObject.getClass(), SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME);
            }
			redisKey = idObj.toString();
		} catch (IllegalAccessException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME, e);
		} catch (InvocationTargetException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME, e);
		}
		return redisKey;
	}
	
	private String getPrincipalIdMethodName() {
		if (SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME == null || "".equals(SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME)) {
			throw new CacheManagerPrincipalIdNotAssignedException();
		}
		return "get" + SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME.substring(0, 1).toUpperCase() + SysConstants.DEFAULT_SHIRO_PRINCIPAL_ID_FIELD_NAME.substring(1);
	}

}
