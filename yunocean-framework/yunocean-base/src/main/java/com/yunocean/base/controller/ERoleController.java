/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  SysRoleController.java   
 * @Package com.yunocean.base.controller   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月5日 上午10:06:48   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunocean.base.common.annotation.Response;
import com.yunocean.base.common.dto.ResponseDto;
import com.yunocean.base.model.ERole;
import com.yunocean.base.service.ERoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**   
 * @ClassName:  SysRoleController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月5日 上午10:06:48   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
//@Slf4j
@Api(tags ="角色管理")
@RestController
@CrossOrigin
@RequestMapping(value = "/role")
public class ERoleController 
{
	@Autowired
	private ERoleService sysRoleService;
	
	
	@ApiOperation(value="创建", notes="", produces="application/json")
	@RequiresPermissions("role:add")
	@PostMapping("/add")
    public ResponseDto add(@RequestBody @ApiParam(name="角色",value="传入json格式",required=true) @Valid ERole sysRole,
    		@Response ResponseDto response) {
		boolean flag = sysRoleService.save(sysRole);
		return flag ? response.success() : response.operFailure();		
	}
	
	@ApiOperation(value="修改", notes="", produces="application/json")
	@RequiresPermissions("role:update")
	@PutMapping("/update")	
    public ResponseDto update(@RequestBody @ApiParam(name="角色",value="传入json格式",required=true) @Valid ERole sysRole,
    		@Response ResponseDto response) {
		boolean flag = sysRoleService.updateById(sysRole);
		return flag ? response.success() : response.operFailure();		
	}
	
	@ApiOperation(value="删除", notes="", produces="application/json")
	@RequiresPermissions("role:delete")
	@PutMapping("/delete")
    public ResponseDto delete(@RequestBody @ApiParam(name="角色",value="传入json格式", required= false) ERole sysRole,
    		@Response ResponseDto response) {
		boolean flag = sysRoleService.delete(sysRole.getId());
		return flag ? response.success() : response.operFailure();		
	}
		
	@ApiOperation(value="查询所有", notes="查询所有角色记录", produces="application/json")
	@ApiResponses({
		@ApiResponse(code = 200, message = "操作成功，返回对象数组", response = ERole.class)
	})
	@RequiresPermissions("role:list")
	@GetMapping("/list")	
    public ResponseDto list(@Response ResponseDto response) {
		List<ERole> listAll = sysRoleService.listAll();
		return response.success(listAll);
	}	
}
