package com.occultus.learncase.unit.ch3;

import org.junit.jupiter.api.Test;
import unit.ch3.ExtensionManagerFactory;
import unit.ch3.FactoryLogAnalyzer;

import static org.junit.jupiter.api.Assertions.assertFalse;

//應屬於深度 3: 方法前注入, 使用假工廠類別, 針對從工廠注入被測試類別的相依物件
public class L3FakeFactoryCase {
    //優點: 設計乾淨, 保證了類別的封裝性
    //缺點:
    //      1. 難以理解(假工廠相依假物件)
    //      2. 需要了解何時要呼叫工廠, 若不曾碰過的程式碼,可能耗費較多供時
    @Test
    public void IsValidLogFileName_BadExtension_ReturnsFalse(){
        FakeExtensionManager fakeExtensionManager = new FakeExtensionManager();
        FakeExtensionManagerFactory.setIExtensionManager(fakeExtensionManager);
        FactoryLogAnalyzer factoryLogAnalyzer = new FactoryLogAnalyzer();

        boolean result = factoryLogAnalyzer.isValidLogFileName("filewithgoodextension.foo");
        assertFalse(result);
    }

}
