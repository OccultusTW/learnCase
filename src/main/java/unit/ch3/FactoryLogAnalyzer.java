package unit.ch3;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FactoryLogAnalyzer {
    private IExtensionManager iExtensionManager;
    //建立物件前，先透過 工廠模式指定 ExtensionFactory
    public FactoryLogAnalyzer(){
        iExtensionManager = ExtensionManagerFactory.create();
    }

    public boolean isValidLogFileName(String fileName) {
        return iExtensionManager.isValid(fileName);
    }
}
