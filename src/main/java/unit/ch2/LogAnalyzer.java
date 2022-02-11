package unit.ch2;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Locale;

@Slf4j
public class LogAnalyzer {
    public boolean wasLastFileNameValid;

    public boolean isValidLogFileName(String fileName) {
        //step1: 測試 !fileName.endsWith(".SLF") 驗證失敗, 移除 !
        //step2: 測試 fileName.endsWith(".SLF"), 驗證失敗, 改成大小寫可接受
        //step3: 測試 fileName.toLowerCase().endsWith(".SLF"), 驗證成功
        wasLastFileNameValid = false;
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
