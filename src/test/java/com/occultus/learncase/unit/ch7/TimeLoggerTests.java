package com.occultus.learncase.unit.ch7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TimeLoggerTests {
    @Test
    public void settingSystemTime_Always_ChangesTime() {
        SystemTime.setDate(LocalDate.of(2021,11,12));
        String outputString = TimeLogger.CreateMessage("aaa");
        Assertions.assertEquals("2021-11-12aaa", outputString);
    }

    @AfterEach
    public void afterEachTest() {
        SystemTime.reset();
    }
}
