package com.ljs.gameserver;

import com.ljs.gameserver.config.DBConfig;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.repository.PlayerRepository;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@SpringBootApplication
public class GameserverApplication{


	@Bean
	public DataSource getDataSource(@Autowired DBConfig dbConfig) throws PropertyVetoException {

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


		PlayerRepository playerRepository=context.getBean(PlayerRepository.class);

		PlayerEntry entry=playerRepository.findById("1");
		System.out.println("====================="+entry);
	}
}
