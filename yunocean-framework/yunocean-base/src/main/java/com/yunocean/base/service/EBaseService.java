/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  BaseServiceImpl.java   
 * @Package com.yunocean.base.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月19日 下午9:50:32   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;


/**
 * 企业表的基出操作实现类
 * 不需要缓存的实体对象service需要继承此类
 * Service编写规则，如果仅操作数据库得对象，service类直接继承 BaseService
 * 如果是需要将数据先缓存到redis里，不要继承BaseService或ServiceImpl
 * 
 * @ClassName:  BaseServiceImpl   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月19日 下午9:50:32   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 * extends ServiceImpl<M, T> 
 */
public class EBaseService<M extends BaseMapper<T>, T>
{
	
	@Autowired
    protected M baseMapper;
	
	/**
	 * 分页查询
	 * @Title: queryPage   
	 * @Description: 公共分页查询，默认以update_time降序排列
	 * @param: @param curPage
	 * @param: @param size
	 * @param: @return      
	 * @return: IPage<T>      
	 * @throws
	 */
	public IPage<T> queryPage(Integer curPage, Integer size) {
		Page<T> page = new Page<>(curPage, size);		
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();		
		queryWrapper.orderByDesc("UPDATE_TIME");
	    return baseMapper.selectPage(page, queryWrapper);
    }
	
	
	public boolean save(T t) {
		boolean flag = SqlHelper.retBool(baseMapper.insert(t));
		return flag;
	}
	
	public boolean delete(Long id) {
		boolean flag = SqlHelper.retBool(baseMapper.deleteById(id));
		return flag;
	}
	
	
	public boolean updateById(T t) {
		boolean flag = SqlHelper.retBool(baseMapper.updateById(t));
		return flag;
	}
}
