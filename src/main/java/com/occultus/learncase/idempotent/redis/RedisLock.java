package com.occultus.learncase.idempotent.redis;

import java.util.concurrent.TimeUnit;

public interface RedisLock {
    boolean tryLock(String key, long timeout, TimeUnit timeUnit);
    void releaseLock(String key);
}
