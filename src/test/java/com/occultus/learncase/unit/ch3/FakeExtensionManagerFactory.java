package com.occultus.learncase.unit.ch3;

public class FakeExtensionManagerFactory {
    private static IExtensionManager manager;

    public static IExtensionManager create() {
        if(manager != null) {
            return manager;
        }
        return new FakeExtensionManager();
    }

    public IExtensionManager getIExtensionManager() {
        return manager;
    }

    public static void setIExtensionManager(IExtensionManager iExtensionManager) {
        manager = iExtensionManager;
    }
}
