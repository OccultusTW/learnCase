package com.occultus.learncase.unit.ch3;

import org.junit.jupiter.api.Test;
import unit.ch3.ExtensionManagerFactory;
import unit.ch3.FactoryLogAnalyzer;

import static org.junit.jupiter.api.Assertions.assertFalse;

//應屬於深度 2: 方法前注入, 針對從工廠注入被測試類別的相依物件
public class L2FactoryCase {
    //優點: 設計乾淨, 保證了類別的封裝性
    //缺點: 需要了解何時要呼叫工廠, 若不曾碰過的程式碼,可能耗費較多供時
    @Test
    public void IsValidLogFileName_BadExtension_ReturnsFalse(){
        FakeExtensionManager fakeExtensionManager = new FakeExtensionManager();
        ExtensionManagerFactory.setIExtensionManager(fakeExtensionManager);
        FactoryLogAnalyzer factoryLogAnalyzer = new FactoryLogAnalyzer();

        boolean result = factoryLogAnalyzer.isValidLogFileName("filewithgoodextension.foo");
        assertFalse(result);
    }
}
