package com.yunocean;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统启动入口类
 * @author KK
 * e-mail javaw@126.com.com
 * https://wwww.yunocean.com
 * @version 0.1
 * @since 2019/12/16 9:50
 */
@MapperScan("com.yunocean")
@SpringBootApplication
public class BaseApplication {
	public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}
