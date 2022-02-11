package com.occultus.learncase.unit.ch3;

import org.junit.jupiter.api.Test;
import unit.ch3.FieldLogAnalyzer;

import static org.junit.jupiter.api.Assertions.assertFalse;

//應屬於深度 1: 針對類別中的參數
public class L1FieldCase {
    //優點: 與建構方式類似, 明確表達相依是否必要存在
    //缺點: 當需要測試的越多，依賴的建構參數可能也越多, 降低可讀性和可維護性
    @Test
    public void IsValidLogFileName_BadExtension_ReturnsFalse(){
        FakeExtensionManager fakeExtensionManager = new FakeExtensionManager();
        FieldLogAnalyzer fieldLogAnalyzer = new FieldLogAnalyzer();
        fieldLogAnalyzer.setIExtensionManager(fakeExtensionManager);

        boolean result = fieldLogAnalyzer.isValidLogFileName("filewithgoodextension.foo");
        assertFalse(result);
    }
}
