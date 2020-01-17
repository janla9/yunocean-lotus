/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  MybatisInterceptor.java   
 * @Package com.yunocean.base.dao.intercept   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月7日 上午10:46:15   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
package com.yunocean.base.dao.intercept;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import com.yunocean.base.auth.ShiroSessionManager;
import com.yunocean.base.model.EBaseModel;
import com.yunocean.base.model.EUser;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据入口，修改时对部分字段进行跟新操作
 * 
 * @ClassName: MybatisInterceptor
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 云海洋智能
 * @date: 2020年1月7日 上午10:46:15
 * 
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved.
 */
@Slf4j
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class CustomInterceptor implements Interceptor 
{
	/**
	 * <p>
	 * Title: intercept
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param invocation
	 * @return
	 * @throws Throwable
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		// String sqlId = mappedStatement.getId();
		// log.info("------sqlId------" + sqlId);
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		Object parameter = invocation.getArgs()[1];
		// log.info("------sqlCommandType------" + sqlCommandType);

		if (parameter == null) {
			return invocation.proceed();
		}

		EUser user = ShiroSessionManager.getInstance().getCurrentSessionUser();
		if (user != null) {
			if (SqlCommandType.SELECT == sqlCommandType) {
				Field[] fields = FieldUtils.getAllFields(parameter.getClass());
				for (Field field : fields) {
					if (EBaseModel.FIELD_ENTERPRISE_ID.equals(field.getName())) {
						field.setAccessible(true);
						if(field.get(parameter) == null) {
							field.set(parameter, user.getEnterpriseId());
						}
						field.setAccessible(false);		
						break;
					}
				}
			} else if (SqlCommandType.INSERT == sqlCommandType) {
				Field[] fields = FieldUtils.getAllFields(parameter.getClass());
				for (Field field : fields) {
					if (EBaseModel.FIELD_ENTERPRISE_ID.equals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, user.getEnterpriseId());
						field.setAccessible(false);						
					} else if(EBaseModel.FIELD_CREATE_ID.contentEquals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, user.getId());
						field.setAccessible(false);	
					}
				}
			} else if (SqlCommandType.UPDATE == sqlCommandType) {
				Field[] fields = null;
				if (parameter instanceof ParamMap) {
					ParamMap<?> p = (ParamMap<?>) parameter;
					// 批量更新报错
					if (p.containsKey("et")) {
						parameter = p.get("et");
					} else {
						parameter = p.get("param1");
					}
					// 更新指定字段时报错
					if (parameter == null) {
						return invocation.proceed();
					}
					fields = FieldUtils.getAllFields(parameter.getClass());
				} else {
					fields = FieldUtils.getAllFields(parameter.getClass());
				}

				for (Field field : fields) {
					if (EBaseModel.FIELD_UPDATE_TIME.equals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, LocalDateTime.now());
						field.setAccessible(false);						
					} else if(EBaseModel.FIELD_UPDATE_ID.equals(field.getName())) {
						field.setAccessible(true);
						field.set(parameter, user.getId());
						field.setAccessible(false);	
					}
				}
			}
		} else {
			log.error("<<DB opera . user is null>>");
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

}
