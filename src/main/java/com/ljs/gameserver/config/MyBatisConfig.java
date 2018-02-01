package com.ljs.gameserver.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.ljs.gameserver.mapper"})
public class MyBatisConfig {

}
