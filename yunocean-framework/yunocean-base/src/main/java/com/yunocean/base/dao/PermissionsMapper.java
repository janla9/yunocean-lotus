/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  EPermissionsMapper.java   
 * @Package com.yunocean.base.dao   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月8日 下午1:03:51   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunocean.base.model.Permissions;

/**   
 * 权限列表
 * @ClassName:  EPermissionsMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月8日 下午1:03:51   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
public interface PermissionsMapper extends BaseMapper<Permissions> 
{
	/**
     * 查询ID最大的
     * @Title: selectMaxId   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    Long selectMaxId();
}
