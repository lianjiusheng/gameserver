package com.ljs.mg.gameserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:jdbc.properties"})
public class DBConfig {

    @Value("${dbconfig.url}")
    private String url;

    @Value("${dbconfig.driverClass}")
    private String driverClass;

    @Value("${dbconfig.user}")
    private String user;

    @Value("${dbconfig.password}")
    private String password;

    @Value("${dbconfig.initialPoolSize}")
    private int initialPoolSize;

    @Value("${dbconfig.minPoolSize}")
    private int minPoolSize;

    @Value("${dbconfig.maxPoolSize}")
    private int maxPoolSize;

    @Value("${dbconfig.acquireIncrement}")
    private int acquireIncrement;

    @Value("${dbconfig.maxIdleTime}")
    private int maxIdleTime;

    @Value("${dbconfig.preferredTestQuery}")
    private String preferredTestQuery;

    @Value("${dbconfig.idleConnectionTestPeriod}")
    private int idleConnectionTestPeriod;

    @Value("${dbconfig.testConnectionOnCheckout}")
    private boolean testConnectionOnCheckout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getAcquireIncrement() {
        return acquireIncrement;
    }

    public void setAcquireIncrement(int acquireIncrement) {
        this.acquireIncrement = acquireIncrement;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public String getPreferredTestQuery() {
        return preferredTestQuery;
    }

    public void setPreferredTestQuery(String preferredTestQuery) {
        this.preferredTestQuery = preferredTestQuery;
    }

    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }

    public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
        this.idleConnectionTestPeriod = idleConnectionTestPeriod;
    }

    public boolean isTestConnectionOnCheckout() {
        return testConnectionOnCheckout;
    }

    public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
        this.testConnectionOnCheckout = testConnectionOnCheckout;
    }


}
