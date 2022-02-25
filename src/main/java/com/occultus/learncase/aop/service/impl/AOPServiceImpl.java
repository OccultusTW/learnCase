package com.occultus.learncase.aop.service.impl;

import com.occultus.learncase.aop.service.AOPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AOPServiceImpl implements AOPService {
    @Override
    public void execute1() {
        log.warn(" 1 >>>> test service Impl ");
    }

    @Override
    public String execute2() {
        log.warn(" 2 >>>> test service Impl");
        return "hello world";
    }

    @Override
    public void execute_throw1() throws Exception {
        log.warn(" 3 >>>> test service Impl");
        throw new Exception("throw 1");
    }

    @Override
    public String execute_throw2() throws Exception {
        log.warn(" 4 >>>> test service Impl");
        if(true) {
            throw new Exception("throw 4");
        }
        return null;
    }
}
