/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  UserRoleService.java   
 * @Package com.yunocean.base.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月4日 上午10:05:44   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunocean.base.dao.ERoleMapper;
import com.yunocean.base.dao.EUserRoleMapper;
import com.yunocean.base.model.ERole;
import com.yunocean.base.model.EUserRole;
import com.yunocean.base.model.Permissions;

/**   
 * 
 * @ClassName:  UserRoleService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月4日 上午10:05:45   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Service
public class ERoleService extends EBaseService<ERoleMapper, ERole>
{
	
	@Resource
	protected EUserRoleMapper eUserRoleMapper;
	
	@Resource
	protected PermissionsService permissionsService;
	
	
	/**
	 * 查询出所有的角色 最大10000条
	 * @Title: selectAll   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: List<SysRole>      
	 * @throws
	 */
	public List<ERole> listAll() {
		QueryWrapper<ERole> queryWrapper = new QueryWrapper<ERole>();
		queryWrapper.orderByDesc("UPDATE_TIME");
		List<ERole> roleList = baseMapper.selectList(queryWrapper);
		return roleList;		
	}
	
	/**
	 * 获取指定用户的所有角色
	 * @Title: listById   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param userId
	 * @param: @return      
	 * @return: List<ERole>      
	 * @throws
	 */
	public List<ERole> listByUserId(Long userId) {
		QueryWrapper<EUserRole> queryWrapper = new QueryWrapper<EUserRole>();
		queryWrapper.lambda().eq(EUserRole::getUserId, userId);
		List<EUserRole> userRoleList = eUserRoleMapper.selectList(queryWrapper);		
		if(CollectionUtils.isEmpty(userRoleList)) return null;		
		//查询出所有的角色
		List<ERole> roleList = listAll();
		if(CollectionUtils.isEmpty(roleList)) return null;
		
		List<ERole> result = new ArrayList<ERole>();
		//遍历
		roleList.forEach(item -> {
			userRoleList.forEach(relation -> {
				if(relation.getRoleId() == item.getId()) {
					result.add(item);
				}
			});
		});
		
		if(CollectionUtils.isNotEmpty(result)) {
			result.forEach(item -> {
				List<Permissions> permissionsList = permissionsService.listByRoleId(item.getId());
				item.setPermissions(permissionsList);
			});
		}		
		return result;
	}	
}
