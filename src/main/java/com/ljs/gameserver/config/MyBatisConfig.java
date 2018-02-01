package com.ljs.gameserver.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@MapperScan(basePackages = {"com.ljs.gameserver.mapper"})
public class MyBatisConfig {

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
    public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }
}
