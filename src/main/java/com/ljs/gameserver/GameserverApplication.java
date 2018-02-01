package com.ljs.gameserver;

import com.ljs.gameserver.config.DBConfig;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.mapper.PlayerEntryMapper;
import com.ljs.gameserver.repository.PlayerEntryRepository;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@SpringBootApplication
@MapperScan(basePackages = {"com.ljs.gameserver.mapper"})//mybatis style 3 ,基于java的配置
@EnableCaching //启用缓存
public class GameserverApplication{

	@Autowired
	private DBConfig dbConfig;

	@Bean
	public DataSource getDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(dbConfig.getDriverClass());
		dataSource.setJdbcUrl(dbConfig.getUrl());
		dataSource.setUser(dbConfig.getUser());
		dataSource.setPassword(dbConfig.getPassword());
		dataSource.setInitialPoolSize(dbConfig.getInitialPoolSize());
		dataSource.setMinPoolSize(dbConfig.getMinPoolSize());
		dataSource.setMaxPoolSize(dbConfig.getMaxPoolSize());
		dataSource.setMaxIdleTime(dbConfig.getMaxIdleTime());
		dataSource.setIdleConnectionTestPeriod(dbConfig.getIdleConnectionTestPeriod());
		dataSource.setPreferredTestQuery(dbConfig.getPreferredTestQuery());
		dataSource.setTestConnectionOnCheckout(dbConfig.isTestConnectionOnCheckout());
		return dataSource;
	}


	public static void main(String[] args) {
	ApplicationContext context= SpringApplication.run(GameserverApplication.class, args);


		PlayerEntryRepository playerRepository=context.getBean(PlayerEntryRepository.class);

		while (true) {

			PlayerEntry entry = playerRepository.findById("1");
			System.out.println("=====================" + entry);

			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
