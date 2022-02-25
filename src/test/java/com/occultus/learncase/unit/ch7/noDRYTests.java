package com.occultus.learncase.unit.ch7;

import org.junit.jupiter.api.Test;

public class noDRYTests extends BasicTests {
    //@Test
    public void analyze_EmptyFile_ThrowsExceptions() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.analyzer("myemptyfile.txt");
        //略...
    }
    //@Test
    public void setting_config_throwsExceptions() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        boolean result = configurationManager.isConfiguration("something");
        //略...
    }
}
