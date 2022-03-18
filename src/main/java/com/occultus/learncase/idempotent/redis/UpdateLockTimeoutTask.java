package com.occultus.learncase.idempotent.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class UpdateLockTimeoutTask implements Runnable {

    private String uuid;
    private String key;
    private RedisTemplate<String,String> redisTemplate;

    public UpdateLockTimeoutTask(String uuid, RedisTemplate redisTemplate, String key) {
        this.uuid = uuid;
        this.key = key;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        // 將以uuid為Key，當前執行緒Id為Value的鍵值對儲存到Redis中
        redisTemplate.opsForValue().set(uuid, String.valueOf(Thread.currentThread().getId()));
        // 定期更新鎖的過期時間
        while (true) {
            redisTemplate.expire(key, 2, TimeUnit.SECONDS);

            try{
                // 每隔3秒執行一次
               Thread.sleep(1000);
            }catch (InterruptedException e){
                //e.printStackTrace();
            }
        }
    }
}
