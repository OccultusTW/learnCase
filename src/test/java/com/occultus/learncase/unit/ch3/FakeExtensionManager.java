package com.occultus.learncase.unit.ch3;

import org.apache.commons.lang3.StringUtils;
import unit.ch3.IExtensionManager;

public class FakeExtensionManager implements IExtensionManager {
    @Override
    public boolean isValid(String fileName) {
        return true;
    }
}
