package com.occultus.learncase.unit.ch3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExtractAndOverrideV1Case {
    @Test
    public void IsValidLogFileName_BadExtension_ReturnsFalse() {
        FakeExtensionManager fakeExtensionManager = new FakeExtensionManager();
        FakeEAOV1FactoryV1LogAnalyzer factoryLogAnalyzer = new FakeEAOV1FactoryV1LogAnalyzer(fakeExtensionManager);
        boolean result = factoryLogAnalyzer.isValidLogFileName("filewithgoodextension.foo");
        assertFalse(result);
    }
}
