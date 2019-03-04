package com.wang.springbootdemo.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * set集合获取
     * @param key
     * @return
     */
    public Set<Object> getMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 集合set中是否存在目标对象
     * @param key
     * @param value
     * @return
     */
    public Boolean isMember(String key, Object value){
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public void addMember(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public void delMember(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key,TimeUnit.MILLISECONDS);
    }

    public void setExpire(String key, Integer time) {
        redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
    }

    public void setValue(String key, Object value) {
        //redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delValue(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
