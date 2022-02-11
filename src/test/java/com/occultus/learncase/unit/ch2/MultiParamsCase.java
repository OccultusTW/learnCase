package com.occultus.learncase.unit.ch2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import unit.ch2.LogAnalyzer;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MultiParamsCase {
    private static Stream<Arguments> IsValidLogFileNameParams() {
        return Stream.of(
                Arguments.of("filewithgoodextension.foo", false),
                Arguments.of("filewithgoodextension.slf", true),
                Arguments.of("filewithgoodextension.SLF", true)
        );
    }
    @ParameterizedTest
    @MethodSource("IsValidLogFileNameParams")
    public void IsValidLogFileName_VariousExtension_CheckThem(String fileName, boolean expected){
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        boolean result = logAnalyzer.isValidLogFileName(fileName);
        assertEquals(expected, result);
    }
}
