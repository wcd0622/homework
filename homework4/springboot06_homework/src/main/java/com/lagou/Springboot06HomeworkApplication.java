package com.lagou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // 开启spring boot基于注解的缓存管理支持
@SpringBootApplication
@MapperScan("com.lagou.mapper")
public class Springboot06HomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot06HomeworkApplication.class, args);
	}

}
