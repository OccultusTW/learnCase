package com.occultus.learncase.unit.ch3;

public class ConstructorLogAnalyzer {
    private IExtensionManager iExtensionManager;

    public ConstructorLogAnalyzer(IExtensionManager iExtensionManager) {
        this.iExtensionManager = iExtensionManager;
    }

    public boolean isValidLogFileName(String fileName) {
        return iExtensionManager.isValid(fileName);
    }
}
