package com.lyu.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author LEE
 */

@Slf4j
public class MybatisRedisCache implements Cache {
    private static final String COMMON_CACHE_KEY = "mybatis";

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private final RedisTemplate<String, Object> redisTemplate;
    private final String nameSpace;
    public MybatisRedisCache(String nameSpace) {
        if (nameSpace == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        redisTemplate = SpringUtil.getBean("redisTemplate");
        this.nameSpace = nameSpace;
    }
    @Override
    public String getId() {
        return this.nameSpace;
    }
    private String getKeys() {
        return COMMON_CACHE_KEY + "::" + nameSpace + "::*";
    }
    private String getKey(Object key) {
        return COMMON_CACHE_KEY + "::" + nameSpace + "::" + DigestUtil.md5Hex(String.valueOf(key));
    }
    @Override
    public void putObject(Object key, Object value) {
        redisTemplate.opsForValue().set(getKey(key), value);
    }
    @Override
    public Object getObject(Object key) {
        try {
            return redisTemplate.opsForValue().get(getKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错 ");
        }
        return null;
    }
    @Override
    public Object removeObject(Object o) {
        Object n = redisTemplate.opsForValue().get(getKey(o));
        redisTemplate.delete(getKey(o));
        return n;
    }
    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(getKeys());
        if (CollectionUtil.isNotEmpty(keys)) {
            assert keys != null;
            redisTemplate.delete(keys);
        }
    }
    @Override
    public int getSize() {
        Set<String> keys = redisTemplate.keys(getKeys());
        if (CollectionUtil.isNotEmpty(keys)) {
            assert keys != null;
            return keys.size();
        }
        return 0;
    }
    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}