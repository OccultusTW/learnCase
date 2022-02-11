package unit.ch3;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EAOFactoryV2LogAnalyzer {
    private IExtensionManager iExtensionManager;

    public boolean isValidLogFileName(String fileName) {
        IExtensionManager iExtensionManager = new ExtensionManager();
        return iExtensionManager.isValid(fileName);
    }
}
