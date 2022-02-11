package unit.ch3;


import lombok.extern.slf4j.Slf4j;
import unit.ch3.ExtensionManager;
import unit.ch3.IExtensionManager;

@Slf4j
public class EAOFactoryV1LogAnalyzer {
    private IExtensionManager iExtensionManager;

    public EAOFactoryV1LogAnalyzer(IExtensionManager iExtensionManager){
        this.iExtensionManager = iExtensionManager;
    }

    public boolean isValidLogFileName(String fileName) {
        return getManager().isValid(fileName);
    }

    public IExtensionManager getManager() {
        return new ExtensionManager();
    }
}
