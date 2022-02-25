package com.occultus.learncase.unit.ch2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StatusChgCase {
    private static Stream<Arguments> IsValidLogFileNameParams() {
        return Stream.of(
                Arguments.of("badfile.foo", false),
                Arguments.of("goodfile.slf", true)
        );
    }

    @ParameterizedTest
    @MethodSource("IsValidLogFileNameParams")
    public void IsValidLogFileName_WhenCalled_ChangeWasLastFileNameValid(String file, boolean expected){
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.isValidLogFileName(file);
        assertEquals(expected, logAnalyzer.wasLastFileNameValid);
    }
}
