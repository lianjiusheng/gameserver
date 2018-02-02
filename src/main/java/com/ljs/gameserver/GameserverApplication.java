package com.ljs.gameserver;

import com.ljs.gameserver.config.DBConfig;
import com.ljs.gameserver.entry.PlayerCommanderEntry;
import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.entry.SkillInfo;
import com.ljs.gameserver.mapper.PlayerCommanderEntryMapper;
import com.ljs.gameserver.repository.PlayerEntryRepository;
import com.ljs.gameserver.util.JSONUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;

@SpringBootApplication
//@MapperScan(basePackages = {"com.ljs.gameserver.mapper"})//mybatis style 3 ,基于java的配置
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


	public static void main(String[] args) throws  Exception{
	ApplicationContext context= SpringApplication.run(GameserverApplication.class, args);


		PlayerEntryRepository playerRepository=context.getBean(PlayerEntryRepository.class);
		PlayerCommanderEntryMapper mapper=context.getBean(PlayerCommanderEntryMapper.class);

		int id=1;
		while (true) {

			PlayerEntry entry = playerRepository.findById("1");
			System.out.println("=====================" + entry);

			PlayerCommanderEntry entry1=new PlayerCommanderEntry();
			entry1.setCommanderId(id++);
			entry1.setOwnerId("1");


			SkillInfo skillInfo=new SkillInfo();
			skillInfo.setSkillLvlInfo(new HashMap<>());
			skillInfo.getSkillLvlInfo().put(1,1);
			skillInfo.getSkillLvlInfo().put(2,2);
			entry1.setSkillInfo(skillInfo);

			//mapper.insert(entry1);

			PlayerCommanderEntry rs= mapper.selectByPrimaryKey("1",1);
			System.out.print(JSONUtil.getJSONString(rs));
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
