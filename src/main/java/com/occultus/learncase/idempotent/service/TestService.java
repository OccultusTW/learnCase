package com.occultus.learncase.idempotent.service;

import com.occultus.learncase.idempotent.entity.Test;
import com.occultus.learncase.idempotent.redis.RedisLock;
import com.occultus.learncase.idempotent.repo.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisLock redisLock;

    @Transactional
    public void selectForUpdate() {
        Test test = testRepository.selectForUpdate(1);
        int count = test.getAccount() - 1;
        if (count < 0) {
            throw new RuntimeException("out of limited");
        }
        test.setAccount(count);
        testRepository.save(test);
        log.warn("sql Lock 1 {}, {}", count, Thread.currentThread().getName());
    }

    private final static String LOCK_KEY_TAG = "Tag";

    /**
     * 透過 redis 計算數量
     * */
    public void redisDistributedDeductCountV1() {
        int count = Integer.parseInt(redisTemplate.opsForValue().get("count"));
        if(count < 0) {
            throw new RuntimeException("out of limited");
        }
        count-=1;
        redisTemplate.opsForValue().set("count", count + "");
    }

    /**
     * redisTemplate 實作
     * 因為 redis 是單執行緒模型, 透過設 "一組值" 來當作入口判斷, 如果尚未刪除或超時,則不給進
     * */
    public void redisDistributedDeductCountV2_RedisTemplate() {
        /**
         * 1. 只需要 key 識別, value 不重要
         * 2. 加入超時機制, 考量到執行時間過長, 導致未解鎖
         * */
        boolean setCountBoolean = redisTemplate.opsForValue().setIfAbsent(LOCK_KEY_TAG, "", 10,  TimeUnit.SECONDS);
        if(!setCountBoolean) {
            throw new RuntimeException("setting count error");
        }

        int count = Integer.parseInt(redisTemplate.opsForValue().get("count"));
        if(count < 0) {
            throw new RuntimeException("out of limited");
        }
        count-=1;
        redisTemplate.opsForValue().set("count", count + "");
        /** 執行業務邏輯完成後, 刪除 */
        redisTemplate.delete(LOCK_KEY_TAG);
    }

    /**
     * Lua Script 實作
     * */
    public void redisDistributedDeductCountV2_LuaScript() {
        String uuid = UUID.randomUUID().toString();

        Boolean isLocked = tryLock(LOCK_KEY_TAG, uuid, "3000");
        if (!isLocked) {
            while (true) {
                isLocked = tryLock(LOCK_KEY_TAG, uuid, "3000");
                if (isLocked) {
                    break;
                }
            }
        }
        int count = Integer.parseInt(redisTemplate.opsForValue().get("count"));
        if(count <= 0) {
            throw new RuntimeException("out of limited");
        }
        count-=1;
        redisTemplate.opsForValue().set("count", count + "");
        this.releaseLock(LOCK_KEY_TAG, uuid);

    }

    // 執行 lua脚本, 取得鎖
    public Boolean tryLock(String key, String requestId, String expireTime) {
        // 指定 lua 脚本, 回傳值
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("tryLock.lua")));
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), requestId, expireTime);
        return 1 == result;
    }

    // 執行 lua脚本, 釋放鎖
    private Boolean releaseLock(String key, String requestId) {
        // 指定 lua 脚本, 回傳值
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("releaseLock.lua")));
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), requestId);
        return 1 == result;
    }

    public void redisDistributedDeductCountV3() {
        if(redisLock.tryLock(LOCK_KEY_TAG, 10, TimeUnit.SECONDS)) {
            int count = Integer.parseInt(redisTemplate.opsForValue().get("count"));
            if(count <= 0) {
                throw new RuntimeException("out of limited");
            }
            count-=1;
            redisTemplate.opsForValue().set("count", count + "");

            redisLock.releaseLock(LOCK_KEY_TAG);
        }
    }
}
