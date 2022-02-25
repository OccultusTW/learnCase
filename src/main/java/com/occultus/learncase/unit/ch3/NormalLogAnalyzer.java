package com.occultus.learncase.unit.ch3;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NormalLogAnalyzer {

    public boolean isValidLogFileName(String fileName) {
        IExtensionManager iExtensionManager = new ExtensionManager();
        return iExtensionManager.isValid(fileName);
    }

}
