package com.wang.springbootdemo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wanghui
 * @version V1.0
 * @title: RedisConfig.java
 * @package: com.wang.springbootdemo.common.config
 * @description: redis配置类
 * @date 2018/7/11 13:29
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.cluster.clusterNodes}")
    private String clusterNodes;

    @Value("${spring.redis.cluster.commandTimeout}")
    private int commandTimeout;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.jedis.pool.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${spring.redis.jedis.pool.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${spring.redis.jedis.pool.minEvictableIdleTimeMills}")
    private int minEvictableIdleTimeMills;

    @Value("${spring.redis.jedis.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.redis.jedis.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMills);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolConfig.setTestOnCreate(true);
        jedisPoolConfig.setTestOnReturn(true);
        return jedisPoolConfig;
    }

    @Bean("jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        //redis cluster
        JedisConnectionFactory factory = new JedisConnectionFactory(redisProperties(),jedisPoolConfig());
        return factory;
    }

    /**
     * redisCluster配置
     *
     * @return
     */
    @Bean
    public RedisClusterConfiguration redisProperties() {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeout", commandTimeout);
        return new RedisClusterConfiguration(new MapPropertySource("RedisProperties", source));
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * @Description: 防止redis入库序列化乱码的问题
     * @return     返回类型
     * @date 2018/4/12 10:54
    */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));  //value序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        return stringRedisTemplate;
    }
}