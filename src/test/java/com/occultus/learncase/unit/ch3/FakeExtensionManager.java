package com.occultus.learncase.unit.ch3;

public class FakeExtensionManager implements IExtensionManager {
    @Override
    public boolean isValid(String fileName) {
        return true;
    }
}
