//package com.pay.aile.bill.service.mail.analyze.config;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisClusterNode;
//import org.springframework.data.redis.connection.RedisNode;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import redis.clients.jedis.JedisPoolConfig;
//
//@Configuration
//@EnableConfigurationProperties(RedisProperties.class)
//@ConditionalOnProperty(name = "redis.cluster")
//public class RedisClusterConfig {
//    private Logger LOG = LoggerFactory.getLogger(RedisClusterConfig.class);
//
//    @Resource
//    private RedisProperties redisProperties;
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
//                redisClusterConfiguration(), jedisPoolConfig());
//        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
//            jedisConnectionFactory.setPassword(redisProperties.getPassword());
//        }
//        return jedisConnectionFactory;
//    }
//
//    public JedisPoolConfig jedisPoolConfig() {
//
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
//        jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
//        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
//        return jedisPoolConfig;
//
//    }
//
//    public RedisClusterConfiguration redisClusterConfiguration() {
//
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        String hosts = redisProperties.getHost();
//        String port = redisProperties.getPort();
//        Set<RedisNode> redisNodes = new HashSet<>();
//        redisNodes.add(new RedisClusterNode(hosts, Integer.valueOf(port)));
//        redisClusterConfiguration.setClusterNodes(redisNodes);
//        redisClusterConfiguration
//                .setMaxRedirects(redisProperties.getMaxRedirects());
//        return redisClusterConfiguration;
//
//    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//
//        @SuppressWarnings("rawtypes")
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(stringSerializer);
//
//        LOG.info("create RedisTemplate success");
//        return redisTemplate;
//    }
//}
