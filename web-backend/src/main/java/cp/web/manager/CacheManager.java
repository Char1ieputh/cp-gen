package cp.web.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * Cos 对象存储操作
 */
@Component
public class CacheManager {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    Cache<String, Object> localCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();

    public void put(String key,Object value){
        localCache.put(key, value);
        redisTemplate.opsForValue().set(key,value,100,TimeUnit.MINUTES);
    }

    public Object get(String key){
        //先从本地缓存获取
        // 查找一个缓存元素， 没有查找到的时候返回null
        Object value = localCache.getIfPresent(key);
        if (value != null){
            return value;
        }

        //本地缓存未命中，尝试从redis获取
        value = redisTemplate.opsForValue().get(key);
        if (value != null){
            localCache.put(key,value);
        }

        return value;
    }

    /**
     * 移除缓存
     * @param key
     */
    public void delete(String key){
        localCache.invalidate(key);
        redisTemplate.delete(key);
    }

}
