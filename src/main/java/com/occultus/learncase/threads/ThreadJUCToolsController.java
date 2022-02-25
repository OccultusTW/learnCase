package com.occultus.learncase.threads;

import com.occultus.learncase.threads.juctools.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/threads")
public class ThreadJUCToolsController {

    @GetMapping("/countDownLatch")
    public void countDownLatch() {
        CountDownLatchService.baseDemo();
//        CountDownLatchService.v1ByNoCountDownLatch();
//        CountDownLatchService.v2ByCountDownLatch();
    }

    @GetMapping("/cyclicBarrier")
    public void cyclicBarrier() {
        CyclicBarrierService.execute();
    }

    @GetMapping("/semaphore")
    public void semaphore() {
        SemaphoreService.execute();
    }

    @GetMapping("/phaser")
    public void phaser() {
        PhaserService.baseNoPhase();
//        PhaserService.dynamicRegister();
//        PhaserService.useMultiPhase();
//        PhaserService.arrayTask();
    }

    @GetMapping("/exchanger")
    public void exchange() {
        ExchangerService.execute();
    }
}
