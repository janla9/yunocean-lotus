/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  StaticConfig.java   
 * @Package com.yunocean.base.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月9日 上午11:41:22   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yunocean.base.service.assist.PermissionsAssist;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**   
 * @ClassName:  StaticConfig   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月9日 上午11:41:22   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Data
@Configuration
public class ResourceConfig 
{
	//控制器权限配置，扫描路径
	@Value("${yunocean.permissions.scanpath}")
    private String permissionsScanPath;
	
	@Bean
	public PermissionsAssist getPermissionsAssist() {
		PermissionsAssist permissionsAssist = PermissionsAssist.getInstance(permissionsScanPath);
		return permissionsAssist;
	}	
}
