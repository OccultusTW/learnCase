package unit.ch3;

import org.apache.commons.lang3.StringUtils;

public class ExtensionManager implements IExtensionManager {
    public boolean wasLastFileNameValid;

    @Override
    public boolean isValid(String fileName) {
        //step1: 測試 !fileName.endsWith(".SLF") 驗證失敗, 移除 !
        //step2: 測試 fileName.endsWith(".SLF"), 驗證失敗, 改成大小寫可接受
        //step3: 測試 fileName.toLowerCase().endsWith(".SLF"), 驗證成功

        if(StringUtils.isBlank(fileName)) {
            throw new NullPointerException();
        }

        if(!fileName.toUpperCase().endsWith(".SLF")) {
            return false;
        }
        wasLastFileNameValid = true;

        return true;
    }
}
