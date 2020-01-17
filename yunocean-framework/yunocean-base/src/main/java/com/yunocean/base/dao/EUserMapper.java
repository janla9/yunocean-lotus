/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  EUser.java   
 * @Package com.yunocean.base.dao   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月6日 下午3:20:24   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunocean.base.model.EUser;

/**   
 * @ClassName:  EUser   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月6日 下午3:20:24   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
public interface EUserMapper extends BaseMapper<EUser> 
{
	/**
     * 查询所有用户信息
     * @return list
     */
    List<EUser> selectAll();
    
    /**
     * 查询ID最大的
     * @Title: selectMaxId   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    Integer selectMaxId();
    
    
    /**
     * 根据条件查询总数
     * @Title: selectCount   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param queryWrapper
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    Integer selectCount(QueryWrapper<EUser> queryWrapper);
}
