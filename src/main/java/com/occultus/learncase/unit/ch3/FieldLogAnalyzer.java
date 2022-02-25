package com.occultus.learncase.unit.ch3;

public class FieldLogAnalyzer {
    private IExtensionManager iExtensionManager;

    public boolean isValidLogFileName(String fileName) {
        return iExtensionManager.isValid(fileName);
    }

    public IExtensionManager getIExtensionManager() {
        return iExtensionManager;
    }

    public void setIExtensionManager(IExtensionManager iExtensionManager) {
        this.iExtensionManager = iExtensionManager;
    }
}
