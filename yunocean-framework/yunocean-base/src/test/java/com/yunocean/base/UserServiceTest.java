package com.yunocean.base;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunocean.base.dao.EUserMapper;
import com.yunocean.base.model.EUser;
import com.yunocean.base.service.EUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest 
{
	//private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Resource
    private EUserService userService;
	@Resource
	private EUserMapper userMapper;
	
	@Test
	public void getOne(){
        //EUser one = userService.getOne(Wrappers.<EUser>lambdaQuery().gt(EUser::getAge, 39));
        //System.out.println(one);
    }
	
	@Test
	public void getOneByUsername() {
		
		QueryWrapper<EUser> queryWrapper = new QueryWrapper<EUser>();
		queryWrapper.lambda().eq(EUser::getUsername, "janla");
		//System.out.println("-----------------");
		//EUser one = userService.getOne(queryWrapper);		
		//System.out.println(one);
		//System.out.println("-----------------");
		
	}
	
	@Test
	public void saveOne() {
		EUser EUser = new EUser();
		//INSERT INTO SYS_USER ( CREATE_TIME, UPDATE_TIME, username, sex, online, level, status, deleted ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) 
		EUser.setUsername("janla2");
		EUser.setNickname("张三");
		//userService.save(EUser);
	}
	
	@Test
	public void updateOne() {
		EUser EUser = new EUser();
		EUser.setId(1l);
		EUser.setNickname("张三");
		//userService.updateById(EUser);
	}
	
	@Test
	public void deleteOne() {
		EUser EUser = new EUser();
		EUser.setId(1l);
		EUser.setUsername("janla");
		userMapper.deleteById(EUser.getId());
		//userService.updateById(EUser);
	}
	
	@Test
	public void queryOne() {
		QueryWrapper<EUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username","aa");
		//List<EUser> userList = userService.list(queryWrapper);
		//userList.forEach(System.out::println);		
	}
	
	@Test
	public void queryPage() {
		QueryWrapper<EUser> queryWrapper = new QueryWrapper<EUser>();
		queryWrapper.lambda().like(EUser::getNickname, "aa");
		queryWrapper.lambda().orderByAsc(EUser::getUpdateTime);
		//queryWrapper.like("username", "aa");
		//Page<EUser> page = new Page<>(1,2);
		//page = userService.page(page, queryWrapper);
		
		//System.out.println("总记录数："+ page.getTotal());
		//List<EUser> userList = page.getRecords();
		//userList.forEach(System.out::println);
	}
	
	@Test
	public void queryAll() {
		
	}
}
