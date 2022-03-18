package com.occultus.learncase.unit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommonService {
    /**switch 覆蓋率 100%, 值沒差
     * Ans: eclipse jacoco 版本 0.8.0 以下有問題(switch case), 因此和 intellij 無關
     */
    public String testSwitch(String status) {
        switch(status) {
            case "1":
                return "1";
            case "2":
                return "2";
            case "3":
                return "3";
            default:
                return "5";
        }
    }
}
