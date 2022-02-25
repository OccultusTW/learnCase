package com.occultus.learncase.threads.juctools;

import com.occultus.learncase.threads.unsafe.ThreadUnsafeExample;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch
 *
 * 場景:
 *  - "指定數量" 的多個待處理任務, "不互相干擾" 可獨立進行, 直到全部都完成
 *
 * 參數:
 *  - 提供一個數值(欲執行的待處理任務數目).
 *
 * 主要方法:
 *  - 執行緒執行完成, 透過 "countDown()" 就減一
 *  - 未倒數至零時, "await()" 將阻塞執行他的執行緒(main), 直到變零或是執行緒被中斷.
 *  - 執行下一步驟的對象是主執行緒
 *
 * 底層實作:
 *  - 仰賴 AbstractQueuedSynchronizer 提供支持
 *  - 阻塞方式: LockSupport.park
 * */
@Slf4j
public class CountDownLatchService {

    /* 基本使用方式 */
    public static void baseDemo() {
        try{
            final int size = 100;
            ThreadUnsafeExample threadUnsafeExample = new ThreadUnsafeExample();
            ExecutorService executorService = Executors.newCachedThreadPool();
            final CountDownLatch countDownLatch = new CountDownLatch(size);
            for(int i=0; i < size; i++) {
                executorService.execute(()->{
                    threadUnsafeExample.add();
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            executorService.shutdown();

            log.warn("{}", threadUnsafeExample.get());
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**一個容器, 提供兩個方法, add, size 寫兩個執行緒, 執行緒A 添加 1~10 到容器中,
     * 執行緒B 實現監控數目, 當數目到 5 個時, 執行緒B 給出提示並結束. */

    /** Object[wait,notify] */
    public static void v1ByNoCountDownLatch() {
        Container container = new Container();
        Object lock = new Object();
        //監控 "先" 啟動等待(wait), 直到數目達到指定時被 notify
        new Thread(()->{
            synchronized (lock){
                log.warn("監控, 先啟動!");
                if(container.getSize() != 5){
                    try {
                        log.warn("監控, 等待!");
                        lock.wait();//掛起後就會釋放鎖
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.warn("監控, 恢復 數目已達到 5, 呼叫另一條並結束!");
                lock.notify();//喚醒下面那條

            }
        }).start();

        new Thread(() ->{
            synchronized (lock) {
                log.warn("新增, 啟動!");
                for(int i=1; i<=10; i++) {
                    container.add(i);
                    log.warn("add ->{}", i);
                    if(container.getSize() == 5) {
                        lock.notify();//喚醒掛起的執行緒,上面拿條

                        try {
                            log.warn("新增, 等待監控執行!");
                            lock.wait();//再讓他掛起,因為 notify() 不會釋放鎖, wait() 才會
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

    /* countDownLatch */
    public static void v2ByCountDownLatch() {
        Container container = new Container();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(()-> {
            try {
                log.warn("監控, 先啟動!");
                if(container.getSize() != 5) {
                    log.warn("監控, 等待!");
                    countDownLatch.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()-> {
            log.warn("新增, 啟動!");
            for(int i=1; i<=10; i++) {
                container.add(i);
                log.warn("add ->{}", i);
                if(container.getSize() == 5) {
                    log.warn("新增, 等待監控執行!");
                    countDownLatch.countDown();
                    log.warn("監控, 恢復 數目已達到 5, 呼叫另一條並結束!");
               }
            }
        }).start();
    }

    private static class Container {
        volatile List list = new ArrayList();

        public void add(int i) {
            list.add(i);
        }
        public int getSize() {
            return list.size();
        }
    }

}
