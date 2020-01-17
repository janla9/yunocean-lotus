/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ShiroConfig.java   
 * @Package com.yunocean.base.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月27日 下午1:00:39   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yunocean.base.auth.ShiroCredentialsMatcher;
import com.yunocean.base.auth.ShiroRealm;
import com.yunocean.base.auth.ShiroSessionListener;
import com.yunocean.base.auth.ShiroSessionManager;
import com.yunocean.base.cache.ShiroCacheManager;
import com.yunocean.base.cache.ShiroSessionCache;
import com.yunocean.base.common.SysConstants;
import com.yunocean.base.controller.filter.ShiroUserFilter;

/**   
 * @ClassName:  ShiroConfig   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月27日 下午1:00:39   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Configuration
public class ShiroConfig 
{

	@Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        //设置自定义的过滤器，解决跨域问题和 options问题
        filterMap.put("authc", new ShiroUserFilter());
        
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        /*
         * anon:所有url都都可以匿名访问，authc:所有url都必须认证通过才可以访问;
                         *   过滤链定义，从上向下顺序执行，authc 应放在 anon 下面
         * */
        //swagger 放行 ------------------- 测试阶段放开
        filterChainDefinitionMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/swagger-ui.html", "anon");
        filterChainDefinitionMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/swagger-resources", "anon");
        filterChainDefinitionMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/v2/api-docs", "anon");
        filterChainDefinitionMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/webjars/springfox-swagger-ui/**", "anon");
        filterChainDefinitionMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/configuration/security", "anon");
        filterChainDefinitionMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/configuration/ui", "anon");
        //swagger end
        //TEST
        //filterChainDefinitionMap.put("/role/*", "anon");
        
        
        //TEST END
        
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/auth/login", "anon");
        // 配置退出 过滤器，其中具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/auth/logout", "logout");
        
        filterChainDefinitionMap.put("/auth/logout", "logout");
        
        
    
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->因为保存在LinkedHashMap中，顺序很重要
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");// 设置/** 为user后，记住我才会生效
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面,前后端分离设置此为controller返回的未登录的接口
        // --------------------------------------------------
        // 前后端分离使用下面设置
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        // 配器shirot认登录累面地址，前后端分离中登录累面跳转应由前端路由控制，后台仅返回json数据, 对应LoginController中unauth请求
        shiroFilterFactoryBean.setLoginUrl("/auth/unauth");// 前后端分离只需要把需要登录返回告诉前端页面即可
        // ---------------------------------------------------
        // 登录成功后跳转的链接,前后端分离不用设置
        // shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/auth/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
	}
	
	
	@Bean
    public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();	
		//securityManager.setRealm(myShiroRealm());
		securityManager.setRealm(shiroRealm());
		securityManager.setSessionManager(sessionManager());
		//配置自定义缓存redis
		//org.crazycake.shiro.RedisSessionDAO;
		securityManager.setCacheManager(getCacheManager());
		return securityManager;
    }

	/**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * 
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * 
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
	
	@Bean
	public CacheManager getCacheManager() {
		ShiroCacheManager shiroChacheManager = ShiroCacheManager.getInstance();
		return shiroChacheManager;
	}
	
	
	/**
	 * 注入session管理
	 * @Title: sessionManager   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: SessionManager      
	 * @throws
	 */
	@Bean
    public SessionManager sessionManager() {
		ShiroSessionManager shiroSessionManager = ShiroSessionManager.getInstance();
		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new ShiroSessionListener());
        shiroSessionManager.setSessionListeners(listeners);
        //设置session超时时间为
        shiroSessionManager.setGlobalSessionTimeout(SysConstants.DEFAULT_SESSION_KEY_TIMEOUT_MILLI);
        //是否开启删除无效的session对象  默认为true
        shiroSessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        shiroSessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        shiroSessionManager.setSessionValidationInterval(SysConstants.DEFAULT_SESSION_VALIDATION_INTERVAL);
        //设置ShiroSessionCache redis
        shiroSessionManager.setSessionDAO(sessionCache());
        return shiroSessionManager;
	}
	
	
	/**
	 * 注入shiro集群session存储适配器
	 * @Title: shiroSessionCache   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: ShiroSessionCache      
	 * @throws
	 */
	@Bean
    public ShiroSessionCache sessionCache() {
		ShiroSessionCache shiroSessionCache = new ShiroSessionCache();
		shiroSessionCache.setSessionIdGenerator(sessionIdGenerator());
		return shiroSessionCache;
    }

	/**
	 * Session ID 生成器
	 * @Title: sessionIdGenerator   
	 * @Description: Session ID 生成器  
	 * @param: @return      
	 * @return: JavaUuidSessionIdGenerator      
	 * @throws
	 */
	@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }
	
	/**
	 *  注入凭证匹配器
	 * @Title: CredentialsMatcher   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: SimpleCredentialsMatcher      
	 * @throws
	 */
	@Bean
    public SimpleCredentialsMatcher CredentialsMatcher() {
		return ShiroCredentialsMatcher.getInstance();
    }
	
	/**
	 * 注入验证方式加入容器
	 * @Title: shiroRealm   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: ShiroRealm      
	 * @throws
	 */
	@Bean
    public ShiroRealm shiroRealm() {
		return ShiroRealm.getInstance();
	}
}
