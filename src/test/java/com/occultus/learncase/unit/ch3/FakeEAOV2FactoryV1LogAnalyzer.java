package com.occultus.learncase.unit.ch3;

import unit.ch3.EAOFactoryV2LogAnalyzer;

public class FakeEAOV2FactoryV1LogAnalyzer extends EAOFactoryV2LogAnalyzer {
    @Override
    public boolean isValidLogFileName(String fileName) {
        return true;
    }
}
