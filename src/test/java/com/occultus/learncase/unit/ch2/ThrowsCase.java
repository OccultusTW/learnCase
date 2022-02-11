package com.occultus.learncase.unit.ch2;

import org.junit.jupiter.api.Test;
import unit.ch2.LogAnalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThrowsCase {
    @Test
    public void IsValidLogFileName_VariousExtension_throws(){
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        assertThrows(NullPointerException.class, () -> logAnalyzer.isValidLogFileName(null));
    }
}
