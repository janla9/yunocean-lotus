/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  EUserService.java   
 * @Package com.yunocean.base.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月6日 下午3:24:29   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunocean.base.cache.RedisHelper;
import com.yunocean.base.dao.EUserMapper;
import com.yunocean.base.model.EUser;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * 企业用户账号处理类
 * @ClassName:  EUserService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月6日 下午3:24:29   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Service
public class EUserService 
{
	@Autowired
	protected RedisHelper redisHelper;

	@Resource
    protected EUserMapper eUserMapper;
	
	/**
	 * 根据用户名获取对象
	 * @Title: getOneByUsername   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param username
	 * @param: @return      
	 * @return: EUser      
	 * @throws
	 */
	public EUser getByUsername(String username) {
		EUser user = (EUser)redisHelper.hGet(EUser.class.getName(), username);
		if(user == null) {
			QueryWrapper<EUser> queryWrapper = new QueryWrapper<EUser>();
			queryWrapper.lambda().eq(EUser::getUsername, username);
			user = eUserMapper.selectOne(queryWrapper);
			if(user != null) {
				redisHelper.hSet(EUser.class.getName(), user.getUsername(), user);
			}
		}
		return user;
	}
	
	/**
	 * 更新用户
	 * @Title: updateEUser   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param user      
	 * @return: void      
	 * @throws
	 */
	public void save(EUser user) {
		redisHelper.hSet(EUser.class.getName(), user.getUsername(), user);
		eUserMapper.updateById(user);
	}
	
	
	/**
	 * @Title: deleteEUser   
	 * @Description: 根据用户名称删除用户
	 * @param: @param user      
	 * @return: void      
	 * @throws
	 */
	public void removeByUsername(String username) {
		redisHelper.hRemove(EUser.class.getName(), username);
		QueryWrapper<EUser> queryWrapper = new QueryWrapper<EUser>();
		queryWrapper.lambda().eq(EUser::getUsername, username);
		eUserMapper.delete(queryWrapper);
	}
}
