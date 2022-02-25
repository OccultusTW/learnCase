package com.occultus.learncase.unit.ch3;

public class FakeEAOV2FactoryV1LogAnalyzer extends EAOFactoryV2LogAnalyzer {
    @Override
    public boolean isValidLogFileName(String fileName) {
        return true;
    }
}
