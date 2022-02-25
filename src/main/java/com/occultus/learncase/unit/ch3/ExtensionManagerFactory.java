package com.occultus.learncase.unit.ch3;

public class ExtensionManagerFactory {
    private static IExtensionManager manager;

    public static IExtensionManager create() {
        if(manager != null) {
            return manager;
        }
        return new ExtensionManager();
    }

    public IExtensionManager getIExtensionManager() {
        return manager;
    }

    public static void setIExtensionManager(IExtensionManager iExtensionManager) {
        manager = iExtensionManager;
    }
}
