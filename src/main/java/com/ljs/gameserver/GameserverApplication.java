package com.ljs.gameserver;

import akka.actor.ActorSystem;
import com.ljs.gameserver.config.DBConfig;
import com.ljs.gameserver.springakka.SpringExtension;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


@SpringBootApplication
//@MapperScan(basePackages = {"com.ljs.gameserver.mapper"})//mybatis style 3 ,基于java的配置
@EnableCaching //启用缓存
public class GameserverApplication {

    @Autowired
    private ApplicationContext applicationContext;

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


    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("actorSystem");
        // initialize the application context in the Akka Spring Extension
        SpringExtension.getInstance().get(system).initialize(applicationContext);



        return system;
    }

    public static void main(String[] args) throws Exception {
//        ApplicationContext context = SpringApplication.run(GameserverApplication.class, args);
//
//        ActorSystem system = context.getBean(ActorSystem.class);
//
//        ActorRef wordActor = system.actorOf(
//                SpringExtension.getInstance().get(system).props("WorldActor"), "WorldActor");
//
//        ActorRef playerEntryRepository = system.actorOf(
//                SpringExtension.getInstance().get(system).props("PlayerEntryRepository"), "PlayerEntryRepository");




    }
}
