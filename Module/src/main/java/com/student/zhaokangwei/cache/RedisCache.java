package com.student.zhaokangwei.cache;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.student.zhaokangwei.holder.ApplicationContextHolder;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 用Redis方式重写MyBatis的缓存机制，目的替换MyBatis自带的基于内容的缓存机制
 */
public class RedisCache implements Cache {

    //缓存实例ID
    String id;

    //Redis操作对象
    RedisTemplate redisTemplate;

    //读写锁
    ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        initBean();
        redisTemplate.opsForValue().set(key.toString(), value);
    }

    @Override
    public Object getObject(Object key) {
        initBean();
        return redisTemplate.opsForValue().get(key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        initBean();
        return redisTemplate.delete(key.toString());
    }

    @Override
    public void clear() {
        initBean();
        //获取Redis里面所有key
        Set<String> keys = redisTemplate.keys("*:" + id + "*");
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public int getSize() {
        initBean();
        return Integer.parseInt(redisTemplate.execute(RedisServerCommands::dbSize).toString());
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return lock;
    }

    /**
     * 初始化Bean
     */
    private void initBean() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
    }

}
