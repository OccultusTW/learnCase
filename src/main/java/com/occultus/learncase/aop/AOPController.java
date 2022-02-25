package com.occultus.learncase.aop;

import com.occultus.learncase.aop.service.AOPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/aop")
public class AOPController {
    @Autowired
    private AOPService aopService;

    @GetMapping("/test")
    public void test() {
        aopService.execute1();
        aopService.execute2();
        try{
//            aopService.execute_throw1();
            aopService.execute_throw2();
        }catch (Exception e){
            //do nothing
        }

    }
}
