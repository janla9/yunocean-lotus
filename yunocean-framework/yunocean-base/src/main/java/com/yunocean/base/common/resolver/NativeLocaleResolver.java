/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  NativeLocaleResolver.java   
 * @Package com.yunocean.base.common.resolver   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月31日 下午4:35:05   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.common.resolver;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

/**   
 * 自定义区域解析器
 * @ClassName:  NativeLocaleResolver   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月31日 下午4:35:05   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
public class NativeLocaleResolver implements LocaleResolver 
{
	private static HashMap<String, Locale> langMap = new HashMap<String, Locale>(2);
	
	private static NativeLocaleResolver instance;
	
	public static NativeLocaleResolver getInstance() {
		if(instance == null) {
			instance = new NativeLocaleResolver();
		}
		return instance;
	}
	
	
	/**   
	 * <p>Title: resolveLocale</p>   
	 * <p>Description: </p>   
	 * @param request
	 * @return   
	 * @see org.springframework.web.servlet.LocaleResolver#resolveLocale(javax.servlet.http.HttpServletRequest)   
	 */ 
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		//获取自定义请求头信息，lang的参数值
        String lang = request.getParameter("lang");
        //获取系统的默认区域信息
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(lang)){
        	locale = langMap.get(lang);
        	if(locale == null) {
        	    String[] split = lang.split("_");
        	    if(ArrayUtils.isNotEmpty(split) && ArrayUtils.getLength(split) > 1) {
	                //接收的第一个参数为：语言代码，国家代码
	                locale = new Locale(split[0],split[1]);
	                langMap.put(lang, locale);
        	    }
        	}
        }
        return locale;
	}

	/**   
	 * <p>Title: setLocale</p>   
	 * <p>Description: </p>   
	 * @param request
	 * @param response
	 * @param locale   
	 * @see org.springframework.web.servlet.LocaleResolver#setLocale(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.util.Locale)   
	 */ 
	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub
		
	}
}
