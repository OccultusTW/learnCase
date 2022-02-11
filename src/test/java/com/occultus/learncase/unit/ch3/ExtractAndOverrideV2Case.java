package com.occultus.learncase.unit.ch3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExtractAndOverrideV2Case {
    @Test
    public void IsValidLogFileName_BadExtension_ReturnsFalse() {
        FakeEAOV2FactoryV1LogAnalyzer fakeEAOV2FactoryLogAnalyzer = new FakeEAOV2FactoryV1LogAnalyzer();

        boolean result = fakeEAOV2FactoryLogAnalyzer.isValidLogFileName("filewithgoodextension.foo");
        assertFalse(result);
    }
}
