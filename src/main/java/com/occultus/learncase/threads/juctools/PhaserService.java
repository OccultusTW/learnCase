package com.occultus.learncase.threads.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Phaser
 *
 * 場景:
 *  - 又稱階段器, 用來解決多執行緒分階段共同完成的任務, 與 CountDownLatch 和 CyclicBarrier 類似,
 *  - 差異在於 可以動態調整 parties 的數量, 能夠 註冊/註銷 哪些需要"共同協調"的執行緒, 因此相對靈活.
 *  - 也支持分層架構, 來達到更高的吞吐量
 *  - 意思: "不同的階段" 可以有 "不同的執行緒數量" 到達
 *
 * 參數:
 *  - parties 指定欲執行的任務數
 *  - phase 最大值到 Integer.MAX_VALUE
 * 主要方法:
 *  - "register()" 註冊 1 個 parties, 也可以透過建構子或是 bulkRegister() 註冊多個.
 *  - "arrive()" 使當前執行緒到達 phaser, 不等待其他任務到達
 *  - "arriveAndAwaitAdvance()" 使當前執行緒到達 phaser, 並等待(阻塞)其他任務到達, arriveAndAwaitAdvance() 等價 awaitAdvance(arrive())
 *  - "awaitAdvance(int phase)" 用於等待(阻塞)執行緒到達, 直到 phaser 前進到下一個 phaser
 *
 * 底層實作:
 *  - 仰賴 long state參數來 標駐狀態, 透過左右移位的方式
 * */
@Slf4j
public class PhaserService {

    public static void baseNoPhase() {
        Phaser phaser = new Phaser(5);

        log.warn("當前 階段數 : {}", phaser.getPhase());
        phaser.register();
        log.warn("當前 parties 數 : {}", phaser.getRegisteredParties());
        phaser.bulkRegister(4);
        log.warn("當前 parties 數 : {}", phaser.getRegisteredParties());

        new Thread(()->{
            phaser.arriveAndAwaitAdvance();
        }).start();

        new Thread(()->{
            phaser.arriveAndDeregister();
            log.warn("go on");
        }).start();

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.warn("當前 parties 數 : {}", phaser.getRegisteredParties());
        log.warn("當前 到達數 : {}", phaser.getArrivedParties());
        log.warn("當前 未到達數 : {}", phaser.getUnarrivedParties());

        log.warn("是否停止 : {}", phaser.isTerminated());
        //當 parties 為0, 或是呼叫 forceTermination() 就會停止, 也可重寫 onAdvance 判斷依據
        phaser.forceTermination();
        log.warn("是否停止 : {}", phaser.isTerminated());
    }

    public static void dynamicRegister() {
        Phaser phaser = new Phaser();
        for(int i=0; i<5; i++) {
            new DynamicTask(phaser).start();
        }
        //主執行緒加入
        phaser.register();
        //等待上面 5 個執行緒
        phaser.arriveAndAwaitAdvance();
    }

    public static void useMultiPhase() {
        Phaser phaser = new Phaser(5);
        for(int i=1; i<=5; i++) {
            new AthleteTask(phaser).start();
        }
    }

    public static void arrayTask() {
        Phaser phaser = new Phaser(5);

        IntStream.rangeClosed(1,5).forEach(i -> new ArrayTask(i, phaser).start());

        log.warn("phase :{}", phaser.getPhase());
        phaser.awaitAdvance(phaser.getPhase());
        log.warn("All worked finished");
    }

    private static class DynamicTask extends Thread {
        private Phaser phaser;

        public DynamicTask(Phaser phaser) {
            this.phaser = phaser;
            this.phaser.register();
        }

        @Override
        public void run() {
            log.warn("The Thread [{}] is working", getName());
            try {
                TimeUnit.SECONDS.sleep( new Random().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.warn("The Thread [{}] work finished", getName());
            //等待其他執行緒完成
            phaser.arriveAndAwaitAdvance();
        }
    }

    private static class AthleteTask extends Thread {
        private Phaser phaser;

        public AthleteTask(Phaser phaser) {
            this.phaser = phaser;
            //這裡就不註冊, 已經指定 parties 數量
        }

        @Override
        public void run() {

            try {
                log.warn("當前處於第  [{}] 階段", phaser.getPhase());
                log.warn("The Thread [{}] start run", getName());
                TimeUnit.SECONDS.sleep( new Random().nextInt(5));
                log.warn("The Thread [{}] work finished", getName());
                //等待其他執行緒完成
                phaser.arriveAndAwaitAdvance();

                log.warn("當前處於第  [{}] 階段", phaser.getPhase());
                log.warn("The Thread [{}] start bicycle", getName() );
                TimeUnit.SECONDS.sleep( new Random().nextInt(5));
                log.warn("The Thread [{}] work finished", getName());
                //等待其他執行緒完成
                phaser.arriveAndAwaitAdvance();

                log.warn("當前處於第  [{}] 階段", phaser.getPhase());
                log.warn("The Thread [{}] start long jump", getName());
                TimeUnit.SECONDS.sleep( new Random().nextInt(5));
                log.warn("The Thread [{}] work finished", getName());
                //等待其他執行緒完成
                phaser.arriveAndAwaitAdvance();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private static class ArrayTask extends Thread {
        private Phaser phaser;

        public ArrayTask(int name, Phaser phaser) {
            super(String.valueOf(name));
            this.phaser = phaser;
        }

        @Override
        public void run() {
            log.warn("{} start working", Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.warn("{} end working", Thread.currentThread().getName());
        }
    }
}
