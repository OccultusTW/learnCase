package com.occultus.learncase.unit.ch3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//應屬於深度 1: 針對類別中的參數
public class L1ConstructorCase {
    //優點: 一目瞭然
    //缺點: 當需要測試的越多，依賴的建構參數可能也越多, 降低可讀性和可維護性
    @Test
    public void IsValidLogFileName_BadExtension_ReturnsFalse(){
        FakeExtensionManager fakeExtensionManager = new FakeExtensionManager();
        ConstructorLogAnalyzer constructorLogAnalyzer = new ConstructorLogAnalyzer(fakeExtensionManager);

        boolean result = constructorLogAnalyzer.isValidLogFileName("filewithgoodextension.foo");
        assertFalse(result);
    }
}
