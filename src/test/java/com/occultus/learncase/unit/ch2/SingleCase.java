package com.occultus.learncase.unit.ch2;

import org.junit.jupiter.api.Test;
import unit.ch2.LogAnalyzer;

import static org.junit.jupiter.api.Assertions.*;

public class SingleCase {
    @Test
    public void IsValidLogFileName_BadExtension_ReturnsFalse(){
        LogAnalyzer logAnalyzer = MakeLogAnalyzer();
        boolean result = logAnalyzer.isValidLogFileName("filewithgoodextension.foo");
        assertFalse(result);
    }

    @Test
    public void IsValidLogFileName_GoodExtensionLowerCase_ReturnsTrue() {
        LogAnalyzer logAnalyzer = MakeLogAnalyzer();
        boolean result = logAnalyzer.isValidLogFileName("filewithgoodextension.slf");
        assertTrue(result);
    }

    @Test
    public void IsValidLogFileName_GoodExtensionUpperCase_ReturnsTrue() {
        LogAnalyzer logAnalyzer = MakeLogAnalyzer();
        boolean result = logAnalyzer.isValidLogFileName("filewithgoodextension.SLF");
        assertTrue(result);
    }

    //工廠方法(factory-method)
    private LogAnalyzer MakeLogAnalyzer() {
        return new LogAnalyzer();
    }
}
