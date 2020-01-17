/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  EPermissionsService.java   
 * @Package com.yunocean.base.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月8日 下午1:15:55   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yunocean.base.cache.RedisHelper;
import com.yunocean.base.dao.ERolePermissionsMapper;
import com.yunocean.base.dao.PermissionsMapper;
import com.yunocean.base.model.ERole;
import com.yunocean.base.model.ERolePermissions;
import com.yunocean.base.model.Permissions;

import lombok.extern.slf4j.Slf4j;

/** 
 * 全量数据缓存到redis  
 * @ClassName:  EPermissionsService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月8日 下午1:15:55   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Slf4j
@Service
public class PermissionsService
{
	@Autowired
	protected RedisHelper redisHelper;
	
	@Resource
    protected PermissionsMapper permissionsMapper;
	
	@Resource
	private ERolePermissionsMapper eRolePermissionsMapper;
	
	private static final String PERMISSIONS_CHAECH_ALL_LIST_FLAG = "PermissionsAllList";
	
	/**
	 * 查询出所有的权限配置
	 * @Title: listAll   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: List<EPermissions>      
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Permissions> listAll() {
		List<Permissions> permsList = (List<Permissions>)redisHelper.hGet(Permissions.class.getName(), PERMISSIONS_CHAECH_ALL_LIST_FLAG);
		if(CollectionUtils.isEmpty(permsList)) {
			QueryWrapper<Permissions> queryWrapper = new QueryWrapper<Permissions>();		
			queryWrapper.orderByDesc("SORT");
			permsList = permissionsMapper.selectList(queryWrapper);
			if(CollectionUtils.isNotEmpty(permsList)) {
				redisHelper.hSet(ERole.class.getName(), PERMISSIONS_CHAECH_ALL_LIST_FLAG, permsList);
			}
		}
		return permsList;
	}
	
	
	/**
	 * 根据角色ID获取此角色下的所有权限
	 * @Title: listByRoleId   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param roleId
	 * @param: @return      
	 * @return: List<EPermissions>      
	 * @throws
	 */
	public List<Permissions> listByRoleId(Long roleId) {
		QueryWrapper<ERolePermissions> queryWrapper = new QueryWrapper<ERolePermissions>();
		queryWrapper.lambda().eq(ERolePermissions::getRoleId, roleId);		
		List<ERolePermissions> rolePermList = eRolePermissionsMapper.selectList(queryWrapper);
		if(CollectionUtils.isEmpty(rolePermList)) return null;
		
		List<Permissions> allPermsList = listAll();
		if(CollectionUtils.isEmpty(allPermsList)) return null;
		
		List<Permissions> result = new ArrayList<Permissions>();
		allPermsList.forEach(item -> {
			rolePermList.forEach(relation -> {
				if(relation.getPermissionsId() == item.getId()) {
					result.add(item);
				}
			});
		});		
		return result;
	}
	
	/**
	 * 获取最大ID值
	 * @Title: getMaxId   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: Long      
	 * @throws
	 */
	public Long getMaxId() {
		QueryWrapper<Permissions> queryWrapper = new QueryWrapper<Permissions>();
		queryWrapper.select("max(ID) maxId");
		List<Map<String, Object>> userList = permissionsMapper.selectMaps(queryWrapper);
		if(CollectionUtils.isEmpty(userList)) return 0l;
		Map<String, Object> idMap = userList.get(0);
		if(MapUtils.isEmpty(idMap)) return 0l;
		Long maxId = (Long)idMap.get("maxId");
		return maxId;
	}
	
	/**
	 * 根据名称获取，名称重复只取第一个
	 * @Title: getByName   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param name
	 * @param: @return      
	 * @return: Permissions      
	 * @throws
	 */
	public Permissions getByName(String name) {
		QueryWrapper<Permissions> queryWrapper = new QueryWrapper<Permissions>();
		queryWrapper.lambda().eq(Permissions::getName, name);	
		List<Permissions> permList = permissionsMapper.selectList(queryWrapper);
		if(CollectionUtils.isNotEmpty(permList)) return null;
		if(permList.size() > 1) {
			log.warn("<<Name:" + name + " , There are multiple records>>");
		}
		return permList.get(0);		
	}
	
	public boolean save(Permissions permissions) {
		boolean flag = SqlHelper.retBool(permissionsMapper.insert(permissions));
		clearCache();
		return flag;
	}
	
	public boolean delete(Long id) {
		boolean flag = SqlHelper.retBool(permissionsMapper.deleteById(id));
		clearCache();
		return flag;
	}
		
	public boolean updateById(Permissions permissions) {
		boolean flag = SqlHelper.retBool(permissionsMapper.updateById(permissions));
		clearCache();
		return flag;
	}	
	
	private void clearCache() {
		redisHelper.hRemove(Permissions.class.getName(), PERMISSIONS_CHAECH_ALL_LIST_FLAG);
	}
}
