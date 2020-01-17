package com.yunocean.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * mybatis分页插件拦截器
 * @author KK
 * e-mail javaw@126.com.com
 * https://wwww.yunocean.com
 * @version 0.1
 * @since 2019/12/16 9:50
 */
@Configuration
public class MybatisPlusConfig 
{
	@Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
