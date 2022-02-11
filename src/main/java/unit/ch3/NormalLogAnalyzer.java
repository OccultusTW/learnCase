package unit.ch3;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class NormalLogAnalyzer {

    public boolean isValidLogFileName(String fileName) {
        IExtensionManager iExtensionManager = new ExtensionManager();
        return iExtensionManager.isValid(fileName);
    }

}
