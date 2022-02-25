package com.occultus.learncase.unit.ch2;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IgnoreCase {
    @Test
    @Disabled("disable")
    public void IsValidLogFileName_VariousExtension_throws(){
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        assertThrows(NullPointerException.class, () -> logAnalyzer.isValidLogFileName(null));
    }
}
