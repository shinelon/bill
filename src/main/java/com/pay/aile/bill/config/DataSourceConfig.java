package com.pay.aile.bill.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

/***
 * DataSourceConfig.java
 *
 * @author shinelon
 *
 * @date 2017年11月1日
 *
 */
@Configuration
@MapperScan("com.pay.aile.bill.mapper*")
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "primary.datasource")
    public DataSource dataSourceInit() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean fb = new MybatisSqlSessionFactoryBean();
        fb.setDataSource(dataSource);
        fb.setTypeAliasesPackage("com.pay.aile.bill.entity");
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*/*.xml"));
        PaginationInterceptor pagination = new PaginationInterceptor();
        fb.setPlugins(new Interceptor[] { pagination });
        GlobalConfiguration gcf = new GlobalConfiguration();
        gcf.setDbType("jdbc:mysql:");
        gcf.setIdType(0);
        gcf.setDbColumnUnderline(true);
        fb.setGlobalConfig(gcf);
        return fb.getObject();
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
