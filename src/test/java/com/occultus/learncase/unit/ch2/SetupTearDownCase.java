package com.occultus.learncase.unit.ch2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import unit.ch2.LogAnalyzer;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetupTearDownCase {
    private LogAnalyzer logAnalyzer = null;
    private static Stream<Arguments> IsValidLogFileNameParams() {
        return Stream.of(
                Arguments.of("filewithgoodextension.foo", false),
                Arguments.of("filewithgoodextension.slf", true),
                Arguments.of("filewithgoodextension.SLF", true)
        );
    }

    @BeforeEach
    public void setup() {
        logAnalyzer = new LogAnalyzer();
    }

    @AfterEach
    public void tearDown() {
        //屬於反模式, 實際使用不需要這麼做
        logAnalyzer = null;
    }

    @ParameterizedTest
    @MethodSource("IsValidLogFileNameParams")
    public void IsValidLogFileName_VariousExtension_ReturnTrue(String fileName, boolean expected){

        boolean result = logAnalyzer.isValidLogFileName(fileName);
        assertEquals(expected, result);
    }
}
