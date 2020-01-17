/**  
 * All rights Reserved, Designed By www.yunocean.com
 * @Title:  Swagger2Config.java   
 * @Package com.yunocean.base.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 云海洋智能    
 * @date:   2020年1月2日 下午8:14:41   
 * @version V0.1
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
package com.yunocean.base.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;

import com.yunocean.base.common.SysConstants;
import com.yunocean.base.common.annotation.Response;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

/**   
 * @ClassName:  Swagger2Config   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 云海洋智能 
 * @date:   2020年1月2日 下午8:14:41   
 *     
 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved. 
 */
@Configuration  
@EnableSwagger2
public class Swagger2Config
{
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
	                   .apiInfo(apiInfo())
	                   .ignoredParameterTypes(Response.class)	//忽略注解是Response的参数生成API文档                   
	                   .select()
	                   .apis(RequestHandlerSelectors.basePackage(SysConstants.SWAGGER_SCAN_BASE_PACKAGE)) 
	                   .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
	                   .build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
                   .title("云海洋企业分布式架构系统 - 中小企业分布式架构提供商") //设置文档的标题
                   .description("分布式架构解决方案 - 为降低企业人力成本而生") // 设置文档的描述
                   .version(SysConstants.SWAGGER_API_DOC_VERSION) // 设置文档的版本信息-> 1.0.0 Version information
                   .termsOfServiceUrl("https://www.yunocean.com") // 设置文档的License信息->1.3 License information
                   .build();
	}
	
	
	/**
	 * 修改swagger的默认访问路径
	 * @Title: swaggerUrlHandlerMapping   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param servletContext
	 * @param: @param order
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: SimpleUrlHandlerMapping      
	 * @throws
	 */
	@Bean
    public SimpleUrlHandlerMapping swaggerUrlHandlerMapping(ServletContext servletContext,
                                                            @Value("${swagger.mapping.order:10}") int order) throws Exception {
        SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, ResourceHttpRequestHandler> urlMap = new HashMap<>();
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/webjars/"));
            pathResourceResolver.setUrlPathHelper(new UrlPathHelper());
 
            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/webjars/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            //设置新的路径
            urlMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/webjars/**", resourceHttpRequestHandler);
        }
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/"));
            pathResourceResolver.setUrlPathHelper(new UrlPathHelper());
 
            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            //设置新的路径
            urlMap.put(SysConstants.SWAGGER_DEFAULT_PATH + "/**", resourceHttpRequestHandler);
        }
        urlHandlerMapping.setUrlMap(urlMap);
        //调整DispatcherServlet关于SimpleUrlHandlerMapping的排序
        urlHandlerMapping.setOrder(order);
        return urlHandlerMapping;
    }
	
	/**
	 * SwaggerUI接口访问
	 * @ClassName:  SwaggerResourceController   
	 * @Description:TODO(这里用一句话描述这个类的作用)   
	 * @author: 云海洋智能 
	 * @date:   2020年1月3日 下午3:23:40   
	 *     
	 * @Copyright: 2020 www.yunocean.com Inc. All rights reserved.
	 */
    @Controller
    @ApiIgnore
    @RequestMapping(SysConstants.SWAGGER_DEFAULT_PATH)
    public static class SwaggerResourceController implements InitializingBean {
 
        @Autowired
        private ApiResourceController apiResourceController;
 
        @Autowired
        private Environment environment;
 
        @Autowired
        private DocumentationCache documentationCache;
 
        @Autowired
        private ServiceModelToSwagger2Mapper mapper;
 
        @Autowired
        private JsonSerializer jsonSerializer;
 
        private Swagger2Controller swagger2Controller;
 
        @Override
        public void afterPropertiesSet() {
            swagger2Controller = new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer);
        }
 
        /**
                         * 首页
         *
         * @return
         */
        @RequestMapping
        public ModelAndView index() {
            ModelAndView modelAndView = new ModelAndView("redirect:" + SysConstants.SWAGGER_DEFAULT_PATH + "/swagger-ui.html");
            return modelAndView;
        }
 
        @RequestMapping("/swagger-resources/configuration/security")
        @ResponseBody
        public ResponseEntity<SecurityConfiguration> securityConfiguration() {
            return apiResourceController.securityConfiguration();
        }
 
        @RequestMapping("/swagger-resources/configuration/ui")
        @ResponseBody
        public ResponseEntity<UiConfiguration> uiConfiguration() {
            return apiResourceController.uiConfiguration();
        }
 
        @RequestMapping("/swagger-resources")
        @ResponseBody
        public ResponseEntity<List<SwaggerResource>> swaggerResources() {
            return apiResourceController.swaggerResources();
        }
 
        @RequestMapping(value = "/v2/api-docs", method = RequestMethod.GET, produces = {"application/json", "application/hal+json"})
        @ResponseBody
        public ResponseEntity<Json> getDocumentation(
                @RequestParam(value = "group", required = false) String swaggerGroup,
                HttpServletRequest servletRequest) {
            return swagger2Controller.getDocumentation(swaggerGroup, servletRequest);
        }
    }
}