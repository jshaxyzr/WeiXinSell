package cn.MrZhang.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.MrZhang.service.RedisService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private RedisService redisService;

    /**
     * 
    * @Title: lock
    * @Description: TODO 加锁
    * @param key 商品ID
    * @param value 当前时间+超时时间
    * @return boolean
     */
    public boolean lock(String key, String value) {

        if (redisService.setIfAbsent(key, value)) {
            return true;
        }

        String currentValue = (String) redisService.get(key);
        // 如果锁过期 防止出现死锁
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            // 获取上一个锁的时间
            String oldValue = (String) redisService.getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }

        }
        return false;
    }

    /**
     * 
    * @Title: unLock
    * @Description: TODO 解锁
    * @param key
    * @param value void
     */
    public void unLock(String key, String value) {

        try {
            String currentValue = (String) redisService.get(key);

            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisService.remove(key);
            }
        } catch (Exception e) {
            log.error("【redis分布式锁】解锁异常" + e);
        }
    }
}
