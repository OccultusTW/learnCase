package com.occultus.learncase.threads.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * Semaphore
 *
 *  場景:
 *   - "指定數量"的令牌(又稱資源 or 訊號量), 允許 n個執行緒 "設定能夠取得的量", 當要取得的量超出限制時會阻塞
 *
 *  參數:
 *   - permits 指定可執行次數
 *
 * 主要方法:
 *  - "acquire()" 請求令牌, 呼叫時就會取走一個 AQS令牌, 若次數不足, 會將執行緒阻塞或者直到執行緒中斷
 *  -             一個執行緒調用次數超出令牌, 也會阻塞.
 *
 *  - "release()" 恢復令牌, 執行完成時呼叫(也可以先呼叫來新增), AQS令牌的數量就會恢復(或新增) 1 個, 大於原令牌數量也會增加
 *
 * 底層實作:
 *  - 底層仰賴 AbstractQueuedSynchronizer 提供支持, 但不會使用到條件佇列(Condition)
 *
 * */
@Slf4j
public class SemaphoreService {
    public final static int SEM_SIZE = 10;

    public static void execute() {
        Semaphore semaphore = new Semaphore(SEM_SIZE);
        ThreadSema threadSema1 = new ThreadSema("t1", semaphore);
        ThreadSema threadSema2 = new ThreadSema("t2", semaphore);

        threadSema1.start();
        threadSema2.start();

        int permits = 5;
        log.warn("{} trying to acquire", Thread.currentThread().getName());

        try {
            semaphore.acquire(permits);
            log.warn("{} acquire successfully", Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            log.warn("{} release successfully", Thread.currentThread().getName());
        }
    }

    private static class ThreadSema extends Thread {
        private Semaphore semaphore;

        public ThreadSema(String name, Semaphore semaphore) {
            super(name);
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            int count = 6;
            log.warn("{} trying to acquire", Thread.currentThread().getName());
            try {
                semaphore.acquire(count);
                log.warn("{} acquire successfully", Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(count);
                log.warn("{} release successfully", Thread.currentThread().getName());
            }
        }
    }
}
