package com.occultus.learncase.unit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonServiceTests {
    private CommonService commonService = new CommonService();

    @Test
    public void switch_status_1() {
        String actual = commonService.testSwitch("1");
        assertEquals("1", actual);
    }

    @Test
    public void switch_status_2() {
        String actual = commonService.testSwitch("2");
        assertEquals("2", actual);
    }

    @Test
    public void switch_status_3() {
        String actual = commonService.testSwitch("3");
        assertEquals("3", actual);
    }

    @Test
    public void switch_status_5() {
        String actual = commonService.testSwitch("5");
        assertEquals("5", actual);
    }
}
