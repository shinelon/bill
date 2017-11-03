package com.pay.aile.bill.service.mail.analyze.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    /** 
     * Max number of "idle" connections in the pool. Use a negative value to 
     * indicate an unlimited number of idle connections. 
     */
    private int maxIdle = 10;

    /** 
     * 最大连接数 
     */
    private int maxTotal = 500;

    private int maxWaitMillis = 3000;

    private String host = "localhost";

    private String port = "localhost";

    private String password;

    /** 
     * Maximum number of redirects to follow when executing commands across the 
     * cluster. 
     */
    private int maxRedirects = 10;

    private String mastername;

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public String getMastername() {
        return mastername;
    }

    public void setMastername(String mastername) {
        this.mastername = mastername;
    }
}
