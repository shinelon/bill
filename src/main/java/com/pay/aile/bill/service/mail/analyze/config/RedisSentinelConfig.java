package com.pay.aile.bill.service.mail.analyze.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(name = "redis.sentinel")
public class RedisSentinelConfig {
    private Logger logger = LoggerFactory.getLogger(RedisSentinelConfig.class);
    @Resource
    private RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
                redisSentinelConfiguration(), jedisPoolConfig());
        logger.info("redisProperties.getPassword()==============="
                + redisProperties.getPassword());
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            jedisConnectionFactory.setPassword(redisProperties.getPassword());
        }
        // 设置访问的db
        jedisConnectionFactory.setDatabase(redisProperties.getDatabase());
        return jedisConnectionFactory;
    }

    public JedisPoolConfig jedisPoolConfig() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        if (redisProperties.getPool() != null) {
            jedisPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
            jedisPoolConfig
                    .setMaxWaitMillis(redisProperties.getPool().getMaxWait());

        }

        return jedisPoolConfig;

    }

    public RedisSentinelConfiguration redisSentinelConfiguration() {

        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        // 主
        redisSentinelConfiguration
                .setMaster(redisProperties.getSentinel().getMaster());
        logger.info("redisProperties   getSentinel  getMaster=========="
                + redisProperties.getSentinel().getMaster());
        Set<RedisNode> redisNodes = new HashSet<RedisNode>();
        logger.info("redisProperties   getSentinel  redisNodes=========="
                + redisProperties.getSentinel().getNodes());

        String[] nodeArray = redisProperties.getSentinel().getNodes()
                .split(";");
        for (String nodeStr : nodeArray) {
            logger.info("nodeStr=========================={}", nodeStr);

            String[] node = nodeStr.split(":");
            redisNodes.add(new RedisNode(node[0], Integer.valueOf(node[1])));
        }

        redisSentinelConfiguration.setSentinels(redisNodes);

        return redisSentinelConfiguration;

    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        @SuppressWarnings("rawtypes")
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);

        logger.info("create RedisTemplate success");
        return redisTemplate;
    }
}
