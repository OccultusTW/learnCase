package com.occultus.learncase.idempotent.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RedisLockImpl implements RedisLock {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private final static ThreadLocal<Integer> threadLocalInteger = new ThreadLocal<>();

    private AtomicInteger ai = new AtomicInteger(500);

    @Override
    public boolean tryLock(String key, long timeout, TimeUnit timeUnit) {
        boolean isLocked;

        if(threadLocal.get() == null) {
            String uuid = UUID.randomUUID().toString();
            threadLocal.set(uuid);

            isLocked = redisTemplate.opsForValue().setIfAbsent(key, uuid, timeout, timeUnit);
            /**
             * 考慮到不能一次拿到鎖,就直接返回錯誤不友善, 改成 "自旋鎖設計"
             * 嘗試獲取鎖失敗，則自旋(一直去要)獲取鎖, 直到成功
             *
             * Tips: 自旋鎖設計要考量到 tomcat 最大使用執行緒數(threads.max)
             * */
            if (!isLocked) {
                while (true) {
                    isLocked = redisTemplate.opsForValue().setIfAbsent(key, uuid, timeout, timeUnit);
                    if (isLocked) {
                        break;
                    }
                }
            }
            threadLocalInteger.set(0);
            // 啟動新的執行緒來定期檢查當前執行緒是否執行完成，並更新過期時間
            new Thread(new UpdateLockTimeoutTask(uuid, redisTemplate, key)).start();
        } else {
            isLocked = true;
        }
        /** "重入鎖"設計 */
        if(isLocked) {
            threadLocalInteger.set(threadLocalInteger.get() + 1);
        }
        return isLocked;
    }

    @Override
    public void releaseLock(String key) {
        String uuid = threadLocal.get();
        //歸一化主要目的, 上鎖人解鎖
        if(uuid.equals(redisTemplate.opsForValue().get(key))) {
            Integer count = threadLocalInteger.get();
            // 重入幾次, 就必須解鎖幾次, 直到計數器減為 0 時, 才能釋放鎖

            if (count == null || --count <= 0) {
                redisTemplate.delete(key);
                threadLocal.remove();
                threadLocalInteger.remove();
                // 獲取更新鎖超時時間的執行緒並中斷
                String threadId = redisTemplate.opsForValue().get(uuid);
                Thread updateLockTimeoutThread = ThreadUtils.findThreadById(Long.valueOf(threadId));
                if (updateLockTimeoutThread != null) {
                    redisTemplate.delete(uuid);
                    // 中斷更新鎖超時時間的執行緒
                    updateLockTimeoutThread.interrupt();
                }
            }
        }
    }
}
