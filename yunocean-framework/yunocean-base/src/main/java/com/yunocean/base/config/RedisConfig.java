/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  RedisConfig.java   
 * @Package com.yunocean.base.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月20日 下午3:51:36   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
package com.yunocean.base.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.yunocean.base.message.RedisMessageAdapter;

/**
 * @ClassName: RedisConfig
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 云海洋智能
 * @date: 2019年12月20日 下午3:51:36
 * 
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved.
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class RedisConfig 
{
	/**
	 * KEY的序列号采用 StringRedisSerializer  VALUE序列号采用默认方式
	 *      备注：如果使用Jackson2JsonRedisSerializer,shiro的session在反序列化时存在问题。 
	 * @Title: redisTemplate   
	 * @Description: 获取缓存操作助手对象
	 * @param: @param factory
	 * @param: @return      
	 * @return: RedisTemplate<String,String>      
	 * @throws
	 */
	@Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
        //以下代码为将RedisTemplate的Value序列化方式由JdkSerializationRedisSerializer更换为Jackson2JsonRedisSerializer
        //此种序列化方式结果清晰、容易阅读、存储字节少、速度快，所以推荐更换
        //@SuppressWarnings({ "rawtypes", "unchecked" })
		//Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);        
//        jackson2JsonRedisSerializer.setObjectMapper(om);
        
        //创建Redis缓存操作助手RedisTemplate对象
      	//StringRedisTemplate是RedisTempLate<String, String>的子类
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }	


	/**
	 * 
	 * @Title: cacheManager   
	 * @Description: 缓存配置管理器
	 * @param: @param factory
	 * @param: @return      
	 * @return: CacheManager      
	 * @throws
	 */
	@Bean
	public CacheManager cacheManager(LettuceConnectionFactory factory) {
		// 以锁写入的方式创建RedisCacheWriter对象
		RedisCacheWriter writer = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
		//以锁写入的方式创建RedisCacheWriter对象
		//RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
		/*
		 * 设置CacheManager的Value序列化方式为JdkSerializationRedisSerializer,
		 * 但其实RedisCacheConfiguration默认就是使用 StringRedisSerializer序列化key，
		 * JdkSerializationRedisSerializer序列化value, 所以以下注释代码就是默认实现，没必要写，直接注释掉
		 */
		// RedisSerializationContext.SerializationPair pair =
		// RedisSerializationContext.SerializationPair.fromSerializer(new
		// JdkSerializationRedisSerializer(this.getClass().getClassLoader()));
		// RedisCacheConfiguration config =
		// RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
		// 创建默认缓存配置对象
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		config.entryTtl(Duration.ofHours(12));
		RedisCacheManager cacheManager = new RedisCacheManager(writer, config);
		return cacheManager;
	}
	
	
	/**
	 * redis消息监听器容器
	 * @Title: container   
	 * @Description: 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * @param: @param connectionFactory
	 * @param: @param listenerAdapter
	 * @param: @return      
	 * @return: RedisMessageListenerContainer      
	 * @throws
	 */
	@Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter, RedisMessageAdapter redisMessageAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //可以添加多个 messageListener
        container.addMessageListener(listenerAdapter, new PatternTopic(redisMessageAdapter.getChannel()));
        return container;
    }

	
	@Bean
    MessageListenerAdapter listenerAdapter(RedisMessageAdapter redisMessageAdapter) {
        return new MessageListenerAdapter(redisMessageAdapter, "receiveMessage");
    }	
}
