/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  redisHelper.java   
 * @Package com.yunocean.base.utils   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月22日 下午4:59:02   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
package com.yunocean.base.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @ClassName: redisHelper
 * @Description: REDIS的工具操作类
 * @author: 云海洋智能
 * @date: 2019年12月22日 下午4:59:02
 * 
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved.
 */
@Component
public class RedisHelper 
{
	private static final Logger logger = LoggerFactory.getLogger(RedisHelper.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	
	/**
	 * 获取锁
	 * @Title: getLock   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param lockId
	 * @param: @param millisecond
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean getLock(String lockId, long millisecond) {
		 Boolean success = redisTemplate.opsForValue().setIfAbsent(lockId, "lock", millisecond, TimeUnit.MILLISECONDS);
		 return success != null && success;
	}
	
	/**
	 * 释放锁
	 * @Title: releaseLock   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param lockId      
	 * @return: void      
	 * @throws
	 */
	public void releaseLock(String lockId) {
		redisTemplate.delete(lockId);
	}
	
	
	/**
	 * @Title: expire   
	 * @Description: 指定缓存的过期时间
	 * @param: @param key
	 * @param: @param time
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean expire(String key,long time){
        try {
            if(time>0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception expire>", ex);
            return false;
        }
    }
	
	
	/**
	 * @Title: getExpire   
	 * @Description: 获取KEY的过期时间  
	 * @param: @param key
	 * @param: @return      
	 * @return: Long      
	 * @throws
	 */
	public Long getExpire(String key){
		try {
			return redisTemplate.getExpire(key,TimeUnit.SECONDS);
		} catch(Exception ex) {
			logger.error("<Redis Exception getExpire>", ex);
			return null;
		}
    }
	
	
	/**
	 * @Title: put 
	 * @Description: 插入单个缓存对象 
	 * @param: @param key 
	 * @param: @param value
	 * value @param: @return @return: boolean @throws
	 */
	public boolean put(String key, Object value) {
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			return true;
		} catch(Exception ex) {
			logger.error("<Redis Exception put key>", ex);
			return false;
		}
	}

	/**
	 * 
	 * @Title: put   
	 * @Description: 设置带有超时时间的缓存对象
	 * @param: @param key
	 * @param: @param value
	 * @param: @param expire
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean put(String key, Object value, long expire) {
		try {
			ValueOperations<String, Object> operations = redisTemplate.opsForValue();
	        operations.set(key, value);
	        redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
	        return true;
		} catch(Exception ex) {
			logger.error("<Redis Exception put key expire>", ex);
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: get   
	 * @Description: 取缓存
	 * @param: @param key
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public Object get(String key) {
		try {
			ValueOperations<String, Object> operations  = redisTemplate.opsForValue();
	        Object result = operations.get(key);
	       return result;	     
		} catch(Exception ex) {
			logger.error("<Redis Exception get key>", ex);
			return null;
		}
	}
	
	
	/**
	 * @Title: incr   
	 * @Description: 递增
	 * @param: @param key
	 * @param: @param delta
	 * @param: @return      
	 * @return: Long      
	 * @throws
	 */
	public Long increment(String key, long delta) {
		if (delta <= 0) {
			throw new RuntimeException("The increment factor must be greater than 0.");
		}
		try {
			return redisTemplate.opsForValue().increment(key, delta);
		} catch(Exception ex) {
			logger.error("<Redis Exception increment>", ex);
			return null;
		}
	}
	
	/**
	 * @Title: decrement   
	 * @Description: 递减   
	 * @param: @param key
	 * @param: @param delta
	 * @param: @return      
	 * @return: Long      
	 * @throws
	 */
	public Long decrement(String key, long delta) {
		if (delta <= 0) {
			throw new RuntimeException("Decrement factor must be greater than 0");
		}
		try {
			return redisTemplate.opsForValue().decrement(key, delta);
		} catch (Exception ex) {
			logger.error("<Redis Exception decrement>", ex);
			return null;
		}
	}
	
	/**
	 * 获取KEY的长度
	 * @Title: size   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param key
	 * @param: @return      
	 * @return: Long      
	 * @throws
	 */
	public Long size(final String key) {
		try {
			return redisTemplate.opsForValue().size(key);
		} catch (Exception ex) {
			logger.error("<Redis Exception size>", ex);
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: remove   
	 * @Description: 单个删除
	 * @param: @param key      
	 * @return: void      
	 * @throws
	 */
	public void remove(final String key) {
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
		}
	}
	
	/**
	 * 
	 * @Title: remove   
	 * @Description: 多个删除
	 * @param: @param keys      
	 * @return: void      
	 * @throws
	 */
	public void remove(final String... keys) {
        for (String key : keys) {
        	remove(key);
        }
    }
	
	/**
	 * 
	 * @Title: extis   
	 * @Description: 是否存在
	 * @param: @param key
	 * @param: @return 是否存在，true为存在，false反之     
	 * @return: boolean      
	 * @throws
	 */
	public boolean extis(final String key) {
        return redisTemplate.hasKey(key);
    }
	
	
	/***
	 * ==========================================
	  *     以下都是HASH的操作 
	 * ==========================================
	 */
	
	/**
	 * @Title: hget   
	 * @Description: hash 获取  
	 * @param: @param key
	 * @param: @param item
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	public Object hGet(String key,String item){
		try {
			return redisTemplate.opsForHash().get(key, item);
		} catch (Exception ex) {
			logger.error("<Redis Exception hget>", ex);
			return null;
		}
    }
	
	/**
	 * @Title: hGet   
	 * @Description: 获取hashKey对应的所有键值 
	 * @param: @param key
	 * @param: @return      
	 * @return: Map<Object,Object>      
	 * @throws
	 */
	public Map<Object,Object> hGet(String key){
		try {
			return redisTemplate.opsForHash().entries(key);
		} catch (Exception ex) {
			logger.error("<Redis Exception hmget>", ex);
			return null;
		}
    }
	
	/**
	 * @Title: hSet   
	 * @Description: HashSet
	 * @param: @param key
	 * @param: @param map
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean hSet(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception hmset>", ex);
            return false;
        }
    }

	/**
	 * @Title: hSet   
	 * @Description: HashSet 并设置时间  
	 * @param: @param key
	 * @param: @param map
	 * @param: @param time
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean hSet(String key, Map<String,Object> map, long time){
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception hmset>", ex);
            return false;
        }
	}
	
	/**
	 * 
	 * @Title: hset   
	 * @Description: 向一张hash表中放入数据,如果不存在将创建
	 * @param: @param key
	 * @param: @param item
	 * @param: @param value
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean hSet(String key,String item,Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception hset>", ex);
            return false;
        }
    }
	
	/**
	 * 
	 * @Title: hset   
	 * @Description: 向一张hash表中放入数据,如果不存在将创建
	 * @param: @param key
	 * @param: @param item
	 * @param: @param value
	 * @param: @param time
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean hSet(String key,String item,Object value,long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if(time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception hset by expire time>", ex);
            return false;
        }
    }
	
	/**
	 * @Title: hRemove   
	 * @Description: 删除hash表中的值 
	 * @param: @param key
	 * @param: @param item      
	 * @return: void      
	 * @throws
	 */
	public void hRemove(String key, Object... item){
		try {
			redisTemplate.opsForHash().delete(key,item);
		} catch (Exception ex) {
			logger.error("<Redis Exception hdel>", ex);
        }
    }
	
	/**
	 * @Title: hHasKey   
	 * @Description: 判断hash表中是否有该项的值
	 * @param: @param key
	 * @param: @param item
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public boolean hHasKey(String key, String item){
		try {
			return redisTemplate.opsForHash().hasKey(key, item);
		} catch (Exception ex) {
			logger.error("<Redis Exception hHasKey>", ex);
			return false;
        }
    }
	
	
	
	/**
	 * @Title: hincrement   
	 * @Description: hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * @param: @param key
	 * @param: @param item
	 * @param: @param by
	 * @param: @return      
	 * @return: Double      
	 * @throws
	 */
    public Long hIncrement(String key, String item, long delta){
    	if (delta <= 0) {
			throw new RuntimeException("hIncrement factor must be greater than 0");
		}
    	try {
    		return redisTemplate.opsForHash().increment(key, item, delta);
    	} catch (Exception ex) {
			logger.error("<Redis Exception hincrement>", ex);
			return null;
        }
    }

    /**
     * hash递减
     * @Title: hdecrement   
     * @Description: hash递减 
     * @param: @param key
     * @param: @param item
     * @param: @param delta
     * @param: @return      
     * @return: double      
     * @throws
     */
    public Long hDecrement(String key, String item, long delta){
    	if (delta <= 0) {
			throw new RuntimeException("hdecrement factor must be greater than 0");
		}
    	try {
    		return redisTemplate.opsForHash().increment(key, item, -delta);
    	} catch (Exception ex) {
			logger.error("<Redis Exception hdecrement>", ex);
			return null;
        }
    }
    
    /***
	 * ==========================================
	 *  HASH的操作结束 
	 * ==========================================
	 */
    

    
    /***
	 * ==========================================
	 *  以下都是SET的操作 
	 * ==========================================
	 */
    
    /**
     * @Title: sMembers   
     * @Description: 根据key获取Set中的所有值
     * @param: @param key
     * @param: @return      
     * @return: Set<Object>      
     * @throws
     */
    public Set<Object> sMembers(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception ex) {
        	logger.error("<Redis Exception smembers>", ex);
            return null;
        }
    }

    /**
     * @Title: sisMember   
     * @Description: 根据value从一个set中查询,是否存在
     * @param: @param key
     * @param: @param value
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean sIsMember(String key,Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception ex) {
        	logger.error("<Redis Exception sisMember>", ex);
            return false;
        }
    }

    /**
     * @Title: sadd   
     * @Description: 将数据放入set缓存
     * @param: @param key
     * @param: @param values
     * @param: @return      
     * @return: long      
     * @throws
     */
    public Long sAdd(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception ex) {
        	logger.error("<Redis Exception sadd>", ex);
            return 0l;
        }
    }

    /**
     * @Title: sSetAndTime   
     * @Description: 将set数据放入缓存
     * @param: @param key
     * @param: @param time 设置超时时间
     * @param: @param values
     * @param: @return      
     * @return: Long      
     * @throws
     */
    public Long sSetAndTime(String key, long time, Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception ex) {
        	logger.error("<Redis Exception sSetAndTime>", ex);
            return 0l;
        }
    }

    /**
     * @Title: sSize   
     * @Description:  获取set缓存的长度
     * @param: @param key
     * @param: @return      
     * @return: long      
     * @throws
     */
    public Long sSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception ex) {
        	logger.error("<Redis Exception sSize>", ex);
            return 0l;
        }
    }

    /**
     * @Title: sRemove   
     * @Description: 移除值为value的
     * @param: @param key
     * @param: @param values
     * @param: @return      
     * @return: Long      
     * @throws
     */
    public Long sRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception ex) {
        	logger.error("<Redis Exception sRemove>", ex);
            return 0l;
        }
    }
    
    /***
	 * ==========================================
	 *    SET的操作结束 
	 * ==========================================
	 */
    
    


    /***
	 * ==========================================
	 *        LIST 的操作开始 
	 * ==========================================
	 */

    
    /**
     * 
     * @Title: lGetRange   
     * @Description: 获取list缓存的内容
     * @param: @param key
     * @param: @param start 开始
     * @param: @param end 结束  0 到 -1代表所有值
     * @param: @return      
     * @return: List<Object>      
     * @throws
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception ex) {
        	logger.error("<Redis Exception lGet>", ex);
            return null;
        }
    }

    /**
     * @Title: lSize   
     * @Description: 获取list缓存的长度
     * @param: @param key
     * @param: @return      
     * @return: Long      
     * @throws
     */
    public Long lSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception ex) {
        	logger.error("<Redis Exception lSize>", ex);
            return 0l;
        }
    }
    
    /**
     * @Title: lGet   
     * @Description: 通过索引 获取list中的值
     * @param: @param key
     * @param: @param index index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @param: @return      
     * @return: Object      
     * @throws
     */
    public Object lGet(String key, long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception ex) {
        	logger.error("<Redis Exception lGet by Index>", ex);
            return null;
        }
    }

    /**
     * @Title: lSet   
     * @Description: 添加对象给list
     * @param: @param key
     * @param: @param value
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean lPush(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception lPush>", ex);
            return false;
        }
    }

    /**
     * @Title: lPush   
     * @Description: 添加对象给list
     * @param: @param key
     * @param: @param value
     * @param: @param time 过期时间
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean lPush(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception lPush add expire time>", ex);
            return false;
        }
    }

    /**
     * @Title: lPushAll   
     * @Description: 添加集合给list
     * @param: @param key
     * @param: @param value
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean lPushAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception lPushAll>", ex);
            return false;
        }
    }

    /**
     * @Title: lPushAll   
     * @Description: 添加集合给list
     * @param: @param key
     * @param: @param value
     * @param: @param time 过期时间
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean lPushAll(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception lPushAll add expire time>", ex);
            return false;
        }
    }

    /**
     * @Title: lSet   
     * @Description: 根据索引替换list中的某条数据
     * @param: @param key
     * @param: @param index 索引
     * @param: @param value
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean lSet(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception ex) {
        	logger.error("<Redis Exception lSet>", ex);
            return false;
        }
    }

    /**
     * @Title: lRemove   
     * @Description: 移除N个值为value
     * @param: @param key
     * @param: @param count 移除多少个
     * @param: @param value
     * @param: @return  移除的个数    
     * @return: long      
     * @throws
     */
    public Long lRemove(String key,long count,Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception ex) {
        	logger.error("<Redis Exception lRemove>", ex);
            return 0l;
        }
    }

    
    /***
	 * ==========================================
	 *        LIST 的操作结束
	 * ==========================================
	 */
    
    
    /**
     * 
     * @Title: keys   
     * @Description: 模糊查询获取key值
     * @param: @param pattern 模糊符号：*    样例：查询 w3c* 
     * @param: @return      
     * @return: Set      
     * @throws
     */   
	public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * @Title: convertAndSend   
     * @Description: 发布消息
     * @param: @param channel
     * @param: @param message      
     * @return: void      
     * @throws
     */
    public void convertAndSend(String channel, Object message){
    	try {
    		redisTemplate.convertAndSend(channel,message);    		
    	} catch (Exception ex) {
        	logger.error("<Redis Exception convertAndSend>", ex);            
        }
    }


    //=========BoundListOperations 用法 start============
    // 根据KEY获取绑定得list直接操作BoundListOperations
    
    public BoundListOperations<String, Object> getBoundListOpt(String boundKey) {
    	try {
    		//绑定操作
    		return redisTemplate.boundListOps(boundKey);  
    	} catch (Exception ex) {
        	logger.error("<Redis Exception getBoundListOpt>", ex);        
        	return null;
        }
    }
}
