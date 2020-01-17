/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  PermissionsAssist.java   
 * @Package com.yunocean.base.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月9日 上午9:12:35   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.service.assist;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunocean.base.cache.RedisHelper;
import com.yunocean.base.common.SysConstants;
import com.yunocean.base.common.annotation.PermissionsParent;
import com.yunocean.base.model.Permissions;
import com.yunocean.base.service.PermissionsService;

import cn.hutool.core.util.ClassUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**   
 * 权限的辅助处理，复杂系统启动初始时，通过解析控制器已配置的 Api ApiOperation RequiresPermissions 生成权限对象存入数据库
 * 通过api的tag 和 ApiOperation的 value 作为唯一性。库里已存在相同忽略掉。
 * @ClassName:  PermissionsAssist   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月9日 上午9:12:35   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
public class PermissionsAssist 
{
	private static final String LOCK_ID = "LOCK_INIT_PERMISSIONS";
	
	private static final Long TIME_OUT = 3 * 60 * 1000l;
		
	@Resource
	private PermissionsService permissionsService;
	@Autowired
	protected RedisHelper redisHelper;
	
	//扫描路径
	private String scanPath;
	
	private static PermissionsAssist instance;
	
	public static PermissionsAssist getInstance(String scanPath) {
		if(instance == null) {
			instance = new PermissionsAssist(scanPath);
		}
		return instance;
	}
	
	private PermissionsAssist(String scanPath) {
		this.scanPath = scanPath;
	}
	
	/**
	 * 初始化
	 * @Title: initData   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	public void init() {
		if(StringUtils.isEmpty(scanPath)) return;
		String[] pathArray = StringUtils.split(scanPath, SysConstants.SPLIT_COMMA);
		if(ArrayUtils.isEmpty(pathArray)) return;
		//获取锁 
		boolean getLockFlag = redisHelper.getLock(LOCK_ID, TIME_OUT);
		if(getLockFlag) {
			List<Permissions> aList = permissionsService.listAll();
			for(String path : pathArray) {
				loadResourcePath(path, aList);
			}
			//释放锁
			redisHelper.releaseLock(LOCK_ID);
		}
	}
	
	private void loadResourcePath(String path, List<Permissions> aList) {
		Set<Class<?>> clazzs = ClassUtil.scanPackage(path);
		if(clazzs == null) return;
		List<Method> wPermList = new ArrayList<Method>();
		for (Class<?> clazz : clazzs) {
			//验证此类是否有效
			if(!validClass(clazz)) continue;
			
			String name = null;
			String url = null;
			//先或者此类的API注解名称
			Api apiAnnotation = clazz.getAnnotation(Api.class);
			if(apiAnnotation == null) continue;
			if(apiAnnotation.tags().length < 1) continue;
			name = apiAnnotation.tags()[0];
			RequestMapping requestAnnotation = clazz.getAnnotation(RequestMapping.class);
			if(requestAnnotation == null) continue;
			if(requestAnnotation.value().length < 1) continue;
			url = requestAnnotation.value()[0];
			//获取权限对象
			Permissions pPermissions = constructorParent(name, url, null, aList);
			// 获取方法上的注解
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {            	
            	String permissionText = getPermissionText(method);
            	
            	String operaName = getOperaName(method);            	
            	//获取URL
            	String cUrl = saxUrl(method);
            	//获取此方法是否配了父类
            	boolean permissionsParentBoolean = method.isAnnotationPresent(PermissionsParent.class);
            	if(permissionsParentBoolean) {
            		//放入队列，待所有主类处理完成再处理这个集合
            		wPermList.add(method);
            	} else {
            		constructorChild(name + "-" + operaName, permissionText, url + cUrl, pPermissions.getId(), aList);
            	}
            }
		}
		
		if(CollectionUtils.isNotEmpty(wPermList)) {
			for(Method method : wPermList) {
				PermissionsParent permissionsParent = method.getAnnotation(PermissionsParent.class);
				String parentName = permissionsParent.name();
				Permissions parentPerm = getByName(parentName, aList);
				String permissionText = getPermissionText(method);            	
            	String operaName = getOperaName(method);            	
            	//获取URL
            	String cUrl = saxUrl(method);
            	constructorChild(parentPerm.getName() + "-" + operaName, permissionText, parentPerm.getUrl() + cUrl, parentPerm.getId(), aList);
			}
		}
	}
	
	private String getPermissionText(Method method) {
		boolean requiresPermissionsBoolean = method.isAnnotationPresent(RequiresPermissions.class);
    	String permissionText = null;
    	if (requiresPermissionsBoolean) {
    		RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
    		if(requiresPermissions.value().length >= 1 ) {
    			permissionText = requiresPermissions.value()[0];
    		}
    	}
    	return permissionText;
	}
	
	private String getOperaName(Method method) {
		boolean apiOperationBoolean = method.isAnnotationPresent(ApiOperation.class);
    	String operaName = null;
    	if (apiOperationBoolean) {
    		ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
    		operaName = apiOperation.value();            		
    	}
    	return operaName;
	}
	
	
	private String saxUrl(Method method) {
		GetMapping getMapping = method.getAnnotation(GetMapping.class);
		if(getMapping != null && getMapping.value().length >= 1) {
			return getMapping.value()[0];
		}
		PutMapping putMapping = method.getAnnotation(PutMapping.class);
		if(putMapping != null && putMapping.value().length >= 1) {
			return putMapping.value()[0];
		}
		PostMapping postMapping = method.getAnnotation(PostMapping.class);
		if(postMapping != null && postMapping.value().length >= 1) {
			return postMapping.value()[0];
		}
		return null;
	}
	
	/**
	 * 只有当类里存在注解为 RequiresPermissions 时有效
	 * @Title: validClass   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param clazz
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	private boolean validClass(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
        	boolean requiresPermissionsBoolean = method.isAnnotationPresent(RequiresPermissions.class);
        	if(requiresPermissionsBoolean) return true;
        }
        return false;
	}
	
	
	private Permissions getByName(String name, List<Permissions> aList) {
		if(CollectionUtils.isNotEmpty(aList)) {
			for(Permissions item : aList) {
				if(item.getName() != null && item.getName().equals(name)) {
					return item;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据名称获取权限对象
	 * @Title: getByName   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param name
	 * @param: @param aList
	 * @param: @return      
	 * @return: Permissions      
	 * @throws
	 */
	private Permissions constructorParent(String name, String url, Long parentId, List<Permissions> aList) {
		if(CollectionUtils.isNotEmpty(aList)) {
			for(Permissions item : aList) {
				if(item.getName() != null && item.getName().equals(name)) {
					return item;
				}
			}
		}
		Permissions perm = new Permissions();
		Long maxId = permissionsService.getMaxId();
		perm.setId(++maxId);
		perm.setName(name);
		perm.setParentId(parentId);
		perm.setUrl(url);
		permissionsService.save(perm);
		aList.add(perm);
		return perm;
	}
	
	
	private void constructorChild(String name, String permission, String url, Long parentId, List<Permissions> aList) {
		Permissions perm = null;
		if(CollectionUtils.isNotEmpty(aList)) {
			for(Permissions item : aList) {
				if(item.getName() != null && item.getName().equals(name)) {
					perm = item;
					break;
				}
			}
		}
		
		if(perm == null) {
			perm = new Permissions();
			Long maxId = permissionsService.getMaxId();
			perm.setName(name);
			perm.setId(++maxId);
			perm.setParentId(parentId);
			aList.add(perm);
			permissionsService.save(perm);
		}
		perm.setUrl(url);
		perm.setPermission(permission);
		permissionsService.updateById(perm);
		
	}
}
