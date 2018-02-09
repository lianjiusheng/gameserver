package com.ljs.mg.gameserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ljs.mg.core.Server;
import com.ljs.mg.gameserver.actor.ActorPathConst;
import com.ljs.mg.gameserver.config.DBConfig;
import com.ljs.mg.gameserver.springakka.SpringExtension;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


@SpringBootApplication
//@MapperScan(basePackages = {"com.ljs.gameserver.mapper"})//mybatis style 3 ,基于java的配置
@EnableCaching //启用缓存
public class GameserverApplication implements ApplicationListener {

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
        ActorSystem system = ActorSystem.create(ActorPathConst.sytemName);
        // initialize the application context in the Akka Spring Extension
        SpringExtension.getInstance().get(system).initialize(applicationContext);
        return system;
    }

    @Bean
    public Server server() {
        Server server = new Server();
        server.setHost("0.0.0.0");
        server.setPort(10000);
        return server;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if (applicationEvent instanceof ApplicationReadyEvent) {
            //初始化Actor
            ActorSystem actorSystem = applicationContext.getBean(ActorSystem.class);
            ActorRef actorRef = actorSystem.actorOf(SpringExtension.getInstance().get(actorSystem).props("AuthenticationService"), "AuthenticationService");
        }
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(GameserverApplication.class, args);

        Server server = context.getBean(Server.class);

        server.start();
    }
}
