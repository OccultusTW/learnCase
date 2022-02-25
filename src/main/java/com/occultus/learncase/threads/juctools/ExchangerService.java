package com.occultus.learncase.threads.juctools;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Exchanger
 *
 * 場景:
 *  - 用於兩個執行緒之間的資料交換, 提供一個同步點, 在這裡可以交換資料.
 *
 * 參數:
 *  - parties 指定欲執行的任務數
 *
 * 主要方法:
 *  - "exchange(V x)" 當到達交換點時, 將資料交給該執行緒, 並接受該執行緒給的對象
 *
 * 底層實作:
 *  - 仰賴  提供支持
 * */
@Slf4j
public class ExchangerService {

    public static void execute() {
        try {
            Exchanger<Integer> exchanger = new Exchanger<Integer>();
            new Producer("", exchanger).start();
            new Consumer("", exchanger).start();

            TimeUnit.SECONDS.sleep(7);
            System.exit(-1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Producer extends Thread {
        private Exchanger<Integer> exchanger;
        private static int data = 0;
        Producer(String name, Exchanger<Integer> exchanger) {
            super("Producer-" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i=1; i<5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = i;
                    log.warn("{} 交換前: {}", getName(), data);
                    data = exchanger.exchange(data);
                    log.warn("{} 交換後: {}", getName(), data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private Exchanger<Integer> exchanger;
        private static int data = 0;
        Consumer(String name, Exchanger<Integer> exchanger) {
            super("Consumer-" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (true) {
                data = 0;
                log.warn("{} 交換前: {}", getName(), data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = exchanger.exchange(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.warn("{} 交換後: {}", getName(), data);
            }
        }
    }
}
