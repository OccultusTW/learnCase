package com.occultus.learncase.unit.ch7;

import org.junit.jupiter.api.AfterEach;
import org.mockito.Mockito;
import unit.ch7.ILogger;
import unit.ch7.LoggingFacility;

public class BasicTests {
    public ILogger FakeTheLogger() {
        LoggingFacility.setiLogger(Mockito.mock(ILogger.class));
        return LoggingFacility.getiLogger();
    }

    @AfterEach
    public void tearDown() {
        LoggingFacility.setiLogger(null);
    }
}
