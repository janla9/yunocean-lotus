/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  SysUserController.java   
 * @Package com.yunocean.base.controller   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月30日 上午10:46:49   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunocean.base.common.annotation.Response;
import com.yunocean.base.common.dto.ResponseDto;
import com.yunocean.base.model.EUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
 * SWAGGER 参考 https://www.cnblogs.com/suizhikuo/p/9397417.html
 * 
 * 用户管理 查询，创建，修改
 * @ClassName:  SysUserController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月30日 上午10:46:49   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
//@Slf4j
@Api(tags ="用户管理")
@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class EUserController 
{	
	@ApiOperation(value="分页查询", notes="", produces="application/json")
	@RequiresPermissions("user:list")	
	//@PermissionsParent(name = "用户管理")	
	//@PermissionsParent权限上级，系统会根据此注解自动生成权限表的数据写入数据库。解决繁琐的权限配置劳动。
	//如果此方法本身就归属于此类的权限返回，此注解可以写，系统会自动在将“分页查询”落为“用户管理”的孩子节点	
	@GetMapping("/{page}")
	public ResponseDto getUserByPage(@PathVariable("page") @ApiParam(name="页码",value="整型数字，如：1（第一页）",required=true) Integer page, @Response ResponseDto response) {
		Subject currentUser = SecurityUtils.getSubject();
		EUser user = (EUser)currentUser.getPrincipal();
		System.out.println(user.getUsername());		
		return response.success();
	}
}
