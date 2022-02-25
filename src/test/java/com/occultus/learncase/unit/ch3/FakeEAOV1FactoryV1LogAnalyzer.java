package com.occultus.learncase.unit.ch3;

public class FakeEAOV1FactoryV1LogAnalyzer extends EAOFactoryV1LogAnalyzer {

    public FakeEAOV1FactoryV1LogAnalyzer(IExtensionManager iExtensionManager) {
        super(iExtensionManager);
    }

    @Override
    public IExtensionManager getManager() {
        return new FakeExtensionManager();
    }
}
