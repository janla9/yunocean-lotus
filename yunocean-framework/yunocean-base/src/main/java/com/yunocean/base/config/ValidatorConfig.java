/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  ValidatorConfig.java   
 * @Package com.yunocean.base.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2019年12月30日 下午10:18:13   
 * @version V0.1
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */  
package com.yunocean.base.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yunocean.base.common.resolver.NativeLocaleResolver;
import com.yunocean.base.common.resolver.ResponseMethodArgumentResolver;

/**   
 * 前端自由切换中英文时，在URL上带上 lang=en_US  不带默认中文，如：http://127.0.0.1:9999/auth/login?lang=en_US
 * 
 * @ClassName:  ValidatorConfig   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2019年12月30日 下午10:18:13   
 *     
 * @Copyright: 2019 www.yunocean.com Inc. All rights reserved. 
 */
@Configuration
public class ValidatorConfig extends WebMvcConfigurationSupport
{
	@Autowired
    private MessageSource messageSource;

    @Override
    public Validator getValidator() {
        return validator();
    }	
  
    @Bean  
    public Validator validator() {  
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();  
        validator.setValidationMessageSource(messageSource);  
        return validator;  
    }  
    
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
	}
    
    /**
	 * 定义swagger的路径
	 * <p>Title: addResourceHandlers</p>   
	 * <p>Description: </p>   
	 * @param registry   
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
	    	.addResourceLocations("classpath:/META-INF/resources/");
	
		registry.addResourceHandler("/webjars/**")
	    	.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
    
    /**
     * 解决跨域问题
     * <p>Title: addCorsMappings</p>   
     * <p>Description: </p>   
     * @param registry   
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry)
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
    	registry.addMapping("/**")
    		.allowedOrigins("*")
    		.allowedHeaders("*")
    		.allowedMethods("*")
    		.allowCredentials(true).maxAge(3600);    		
	}
    
    //区域解析器
    @Bean
    public LocaleResolver localeResolver(){
        return NativeLocaleResolver.getInstance();
    }
    
    //参数注入器
    @Bean
    public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
    	return new ResponseMethodArgumentResolver(messageSource, NativeLocaleResolver.getInstance());
    }
    
    
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	argumentResolvers.add(new ResponseMethodArgumentResolver(messageSource, NativeLocaleResolver.getInstance()));
	}
}
