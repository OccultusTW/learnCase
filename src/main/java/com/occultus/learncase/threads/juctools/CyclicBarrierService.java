package com.occultus.learncase.threads.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 *
 * 場景:
 *  - "指定數量" 的所有等待執行緒都完成時, 再繼續執行
 *
 * 參數:
 *  - parties, 提供一個數值(欲執行的待處理任務數目).
 *  - barrierAction, 由最後一個觸發屏障的執行緒去執行
 *
 * 主要方法:
 *  - "await()" 執行緒等待的手段[trip.await()], 並上鎖確認 [屏障是否已破壞,執行緒是否中斷], 是否繼續進行
 *  - "nextGeneration()" , 都完成時[trip.signalAll()]
 *  - 執行下一步驟的對象是其他執行緒
 *
 * 底層實作:
 *  - 可重入鎖, ReentrantLock lock = new ReentrantLock();
 *  - 條件佇列, Condition trip = lock.newCondition();
 *  - ReentrantLock, Condition 底層仰賴 AbstractQueuedSynchronizer 提供支持.
 * */
@Slf4j
public class CyclicBarrierService {
    public static void execute() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Thread("barrierAction") {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " barrier action");
            }
        });
        try {
            ThreadCb threadCb1 = new ThreadCb("t1", cyclicBarrier);
            ThreadCb threadCb2 = new ThreadCb("t2", cyclicBarrier);
            threadCb1.start();
            threadCb2.start();

            log.warn("{} going to await", Thread.currentThread().getName());
            cyclicBarrier.await();
            log.warn("{} continue", Thread.currentThread().getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ThreadCb extends Thread {
        private CyclicBarrier cyclicBarrier;
        public ThreadCb(String name, CyclicBarrier cb) {
            super(name);
            this.cyclicBarrier = cb;
        }

        public void run() {
            log.warn(Thread.currentThread().getName() + " inner thread cb, going to await");
            try {
                cyclicBarrier.await();
                log.warn(Thread.currentThread().getName() + " inner thread cb, continue");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


